package commands

import InputException
import ServerMessage
import Storage
import Ticket
import TicketType
import java.lang.NumberFormatException

/**
 * Класс, реализующий команду filter_less_than_type, отображающие элементы, у которых значение типа меньше, чем заданное
 */
class FilterLessThanType() : AbstractCommand() {
    override val cmd = "filter_less_than_type"
    override val info = "вывести элементы, значение поля type которых, меньше чем заданное "
    override val ticket = false
    private var arg = ""
    override val par = true
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var wasFound: Boolean = false
        var msg = ""
        try {
            var searchType: TicketType = when (arg) {
                "VIP" -> TicketType.VIP
                "USUAL" -> TicketType.USUAL
                "BUDGETARY" -> TicketType.BUDGETARY
                "CHEAP" -> TicketType.CHEAP
                else -> throw InputException("")
            }

            collection.forEach {
                if (it.type!! < searchType) {
                    msg.plus(it.toString() + "\n")
                    wasFound = true
                } else {
                    if (it.equals(collection.last()) and (!wasFound)) {
                        msg = "Не было найдено таких элементов"
                    }
                }
            }
        }
        catch(e: InputException){
            msg = "Формат аргумента неверен"
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){
        this.arg = a
    }

}