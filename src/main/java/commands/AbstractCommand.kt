package commands

import ServerMessage
import Storage
import Ticket

/**
 * Абстрактный класс команды, являющийся родителем для всех классов команд.
 */
abstract class AbstractCommand {
    abstract val cmd: String
    abstract val info: String
    abstract val ticket: Boolean
    abstract val par: Boolean
    constructor(){
    }
    /**
     * Абстрактный метод, реализующий выполнение команд
     */
    abstract fun execute(collection: HashSet<Ticket>) : ServerMessage
    abstract fun setTick(t: Ticket)
    abstract fun setArg(a: String)
}