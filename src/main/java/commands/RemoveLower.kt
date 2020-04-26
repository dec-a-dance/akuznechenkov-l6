package commands

import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException
import java.util.logging.Logger

/**
 * Класс, реализующий команду remove_lower, которая удаляет все элементы, цена которых меньше, чем у заданного
 */
class RemoveLower(): AbstractCommand() {
    override val cmd = "remove_lower"
    override val info = "убрать все элементы, значение которых меньше, чем у входящего"
    val logger = Logger.getLogger("log.txt")
    override val ticket = true
    private var arg = ""
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var wasFound: Boolean = false
        var msg = ""
        try {
            var searchPrice: Long = arg.toLong()
            collection.removeIf{it.price < searchPrice}
            msg = "Команда выполнена"
            logger.info("Элементы ценой ниже $searchPrice удалены из коллекции")
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