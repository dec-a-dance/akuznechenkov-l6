package commands

import ServerMessage
import Ticket
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.util.logging.Logger


/**
 * Класс, реализующий команду exit, завершающую работу консоли без сохранения
 */
class Exit: AbstractCommand() {
    override val cmd = "exit"
    val logger = Logger.getLogger("log.txt")
    override val info = "завершает работу программы без сохранения"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var fileout = FileOutputStream("collection.json")
        var writer = BufferedOutputStream(fileout)
        var mapper: ObjectMapper = ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        var json: String = mapper.writeValueAsString(collection);
        var output = json.toByteArray()
        writer.write(output)
        writer.close()
        logger.info("Работа клиента была завершена")
        return ServerMessage("работа клиента завершена")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }

}