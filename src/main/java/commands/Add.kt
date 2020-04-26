package commands

import Person
import Ticket
import TicketType
import Coordinates
import Location
import InputException
import ServerMessage
import Storage
import java.time.LocalDateTime

/**
 * Класс, реализующий команду add, доавляющую элемент в коллекцию
 */
class Add constructor(): AbstractCommand() {
    override val cmd = "add"
    override val info = "добавляет новый элемент в коллекцию"
    private var tick = Ticket()
    override val ticket = true
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>) : ServerMessage {
        var id = 0L
        if (collection.size > 0) {
            id = collection.maxBy { it.id }!!.id + 1
        }
        else{
            id = 1
        }
        tick.setId(id)
        collection.add(tick)
        var msg = "Элемент добавлен в коллекцию, ему присвоен id = $id"
        return ServerMessage(msg)
    }

    override fun setTick(t: Ticket){
        tick = t
    }
    override fun setArg(a: String){

    }
}