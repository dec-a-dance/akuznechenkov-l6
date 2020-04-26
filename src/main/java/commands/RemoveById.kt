package commands

import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException
import java.util.logging.Logger

/**
 * Класс, реализующий команду remove_by_id, удаляющую элемент из коллекции по его id
 */
class RemoveById() : AbstractCommand() {
    override val cmd = "remove_by_id"
    override val info = "удалить элемент коллекции по его id"
    override val ticket = false
    val logger = Logger.getLogger("log.txt")
    override val par = true
    private var arg = ""
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var msg = ""
        var wasFound = false
        try {
            var searchId: Long = arg.toLong()
            collection.removeIf{it.id == searchId}
            msg = "Команда выполнена"
            logger.info("Элемент с id = $searchId удален из коллекции")
        }
        catch(e: NumberFormatException){
            msg = "Формат аргумента неверен"
            logger.warning("Пользователь отправил неверный аргумент")
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){
        this.arg = a
    }

}