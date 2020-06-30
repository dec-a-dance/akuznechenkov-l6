import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction
import java.util.logging.Logger

class Server(dm: DatabaseManager): RecursiveAction() {
    var databaseManager = DatabaseManager()
    val logger = Logger.getLogger(this.javaClass.toString())
    val PORT = 3333

    override fun compute() {
        val databaseManager = DatabaseManager()
        val logger: Logger = Logger.getLogger("log.txt")
        var collection: HashSet<Ticket> = hashSetOf()
        logger.info("Пытаемся открыть канал по порту $PORT")
        var socket: SocketAddress? = InetSocketAddress(PORT)
        val serializator = Serializator()
        var channel = DatagramChannel.open()
        channel.bind(socket)
        channel.configureBlocking(false)
        logger.info("Канал открыт и готов к приему сообщений")
        while (true) {
            var buffer = ByteBuffer.allocate(10000)
            buffer.clear()
            socket = channel.receive(buffer)
            buffer.flip()
            val b = buffer.array()
            if (String(b).trim().isNotBlank()) {
                val data = serializator.deserialize(b)
                if (data is DataStor) {
                    logger.info("Получен объект: ${data.toString()}")
                    var message = data.unpack(collection, databaseManager)
                    logger.info("Команда ${data.name} приведена в исполнение")
                    buffer.clear()
                    buffer = ByteBuffer.wrap(serializator.serialize(message))
                    channel.send(buffer, socket)
                    logger.info("Ответ отправлен на клиент")
                } else if (data is LoginRequest) {
                    logger.info("Получен запрос на вход.")
                    data.verify(databaseManager)
                    if (data.res) {
                        logger.info("Выполнен вход или регистрация с логином ${data.login}")
                    } else {
                        logger.warning("Вход или регистрация не были выполнены")
                    }
                    databaseManager.updateCollection(collection)
                    var answer = LoginAnswer(data.res)
                    println(answer.toString())
                    buffer.clear()
                    buffer = ByteBuffer.wrap(serializator.serialize(answer))
                    println(serializator.deserialize(buffer.array()).toString())
                    channel.send(buffer, socket)
                    logger.info("Отправлен ответ")
                }
            }
        }
    }
}

fun main(args: Array<String>?) {
    var creationDate = LocalDate.now()
    val databaseManager = DatabaseManager()
    val server = Server(databaseManager)
    val logger = Logger.getLogger("Server")
    logger.info("Запускаем работу сервера по порту ${server.PORT}")
    val fjPool = ForkJoinPool(Runtime.getRuntime().availableProcessors())
    fjPool.invoke(server)
}