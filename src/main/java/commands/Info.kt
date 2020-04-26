package commands

import ServerMessage
import Storage
import Ticket

/**
 * Класс, реализующий команду info, выводящая информацию о коллекции, с которой работает юзер.
 */
class Info(): AbstractCommand() {
    override val cmd = "info"
    override val info = "вывести в стандартный поток вывода информацию о коллекции"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage {
        return ServerMessage("В коллекции сейчас ${collection.size} элементов \n" +
                "Тип коллекции ${collection::class.toString()}")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }

}