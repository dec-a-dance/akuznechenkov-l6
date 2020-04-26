package commands

import ServerMessage
import Ticket


/**
 * Класс, реализующий команду clear, очищающую коллекцию
 */
class Clear(): AbstractCommand() {
    override val cmd = "clear"
    override val info = "очищает коллекцию"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage {
        collection.clear()
        return ServerMessage("Коллекция очищена")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }
}