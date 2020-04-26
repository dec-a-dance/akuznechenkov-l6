package commands

import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException
import java.util.logging.Logger

/**
 * Класс, реализующий команду update, которая заменят объект коллекции с заданным id
 */
class Update(): AbstractCommand() {
    val logger = Logger.getLogger("log.txt")
    override val cmd = "update"
    override val info = "обновить значение элемента коллекции, id которого равен заданному"
    override val ticket = true
    override val par = true
    private var tick = Ticket()
    private var arg = ""
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>) : ServerMessage {
        var id = arg.toLong()
        var msg = ""
        var wasFound = false
        collection.forEach(){
            if (it.id == id){
                collection.remove(it)
                tick.setId(id)
                collection.add(tick)
                logger.info("Элемент с id = $id обновлен")
                msg = "Элемент c id = $id успешно обновлен"
            }
            else{
                if (it == collection.last()){
                    msg = "Элемент с таким id не найден"
                    logger.warning("Пользователь запросил id, которого нет в коллекции")
                }
            }
        }

        return ServerMessage(msg)
    }

    override fun setTick(t: Ticket){
        this.tick = t
    }
    override fun setArg(a: String){
        this.arg = a
    }
}