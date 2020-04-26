import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.time.LocalDateTime
import java.util.logging.Logger

class ServerMain() {

}
fun main(args: Array<String>?) {
    val PORT = 4334
    val logger: Logger = Logger.getLogger("log.txt")
    var collection: HashSet<Ticket> = checkfile("collection.json")
    logger.info("Коллекция считана: ${collection.toString()}")
    logger.info("Пытаемся открыть канал по порту $PORT")
    var socket: SocketAddress? = InetSocketAddress(PORT)
    val serializator = Serializator()
    var channel = DatagramChannel.open()
    channel.bind(socket)
    channel.configureBlocking(false)
    logger.info("Канал открыт и готов к приему сообщений")
    while(true) {
            var buffer = ByteBuffer.allocate(100000)
            buffer.clear()
            socket = channel.receive(buffer)
        buffer.flip()
            val b = buffer.array()
                if (String(b).trim().isNotBlank()) {
                    val data = serializator.deserialize(b)
                    if (data is DataStor) {
                        logger.info("Получен объект: ${data.toString()}")
                        var message = data.unpack(collection)
                        logger.info("Команда ${data.getName} приведена в исполнение")
                        buffer.clear()
                        buffer = ByteBuffer.wrap(serializator.serialize(message))
                        channel.send(buffer, socket)
                        logger.info("Ответ отправлен на клиент")
                        }
                    }
                }
            }



fun checkfile(filename: String): HashSet<Ticket>{
    var mapper = ObjectMapper()
    val logger = Logger.getLogger("log.txt")
    var collection: HashSet<Ticket> = hashSetOf()
    try {
        val fileInput = FileInputStream(File(filename))
        val reader = BufferedInputStream(fileInput)
        val list: Array<Ticket>? = (mapper.readValue(reader, Array<Ticket>::class.java) ?: throw UserFileException())
        reader.close()
        fileInput.close()
        var array = listOf(Long)
        collection = list!!.toHashSet()
        var max: Long = 0
        collection.forEach(){ ticket ->
            var tick = ticket
            if (ticket.price <= 0.0){
                logger.warning("В коллекции найден элемент с инвалидными значениями, работаем с пустой коллекцией")
                collection = hashSetOf()
            }
            if (ticket.person.location!!.x <= 0){
                logger.warning("В коллекции найден элемент с инвалидными значениями, работаем с пустой коллекцией")
                collection = hashSetOf()
            }
            if (ticket.name == ""){
                logger.warning("В коллекции найден элемент с инвалидными значениями, работаем с пустой коллекцией")
                collection = hashSetOf()
            }
            if (ticket.coordinates.y <= -103.0){
                logger.warning("В коллекции найден элемент с инвалидными значениями, работаем с пустой коллекцией")
                collection = hashSetOf()
            }
            if (ticket.person.location!!.name.length > 852){
                logger.warning("В коллекции найден элемент с инвалидными значениями, работаем с пустой коллекцией")
                collection = hashSetOf()
            }
            if (tick.id > max){
                max = tick.id
            }
            var counter = 0
            collection.forEach(){
                if (tick.id == it.id){
                    counter += 1
                }
            }
            if (counter > 1){
                collection = hashSetOf()
                logger.warning("В коллекции были найдены элементы с одинаковым id, считывание не удалось")
            }
            var datetime = LocalDateTime.parse(ticket.creationDate)
            if (datetime > LocalDateTime.now()){
                logger.warning("В коллекции найден элемент, созданный в будущем, считывание не удалось")
            }


        }
    }
    catch(e: UserFileException){
        logger.warning("файл пуст, создается пустая коллекция")
    }
    catch(e: Exception){
        logger.warning("проблемы с входным файлом, работаем с пустой коллекцией")

    }
    return collection

}

