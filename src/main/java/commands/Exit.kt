package commands

import DatabaseManager
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
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage{
        logger.info("Работа клиента ${databaseManager.USER} была завершена")
        return ServerMessage("работа клиента завершена")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }

}