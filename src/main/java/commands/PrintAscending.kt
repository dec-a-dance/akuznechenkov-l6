package commands

import ServerMessage
import Storage
import Ticket

/**
 * Класс, реализующий команду print_ascending, которая выводит элементы коллекции по возрастанию
 */
class PrintAscending(): AbstractCommand() {
    override val cmd = "print_ascending"
    override val info = "вывести элементы коллекции в порядке возрастания"
    override val ticket = false
    val comp = TicketComparator()
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage {
        var msg = ""
        var sorted = collection.toSortedSet(comp)
        sorted.forEach(){
            msg = msg + it.toString() + "\n"
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }
}