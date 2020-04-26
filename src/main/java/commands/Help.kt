package commands

import ServerMessage
import Storage
import Ticket

/**
 * Класс, реализующий команду help, которая выводи справку по доступным командам
 */
class Help(gmanager: CommandManager): AbstractCommand() {
    override val cmd = "help"
    private val manager = gmanager
    override val info = "вывести справку по доступным командам"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage {
        var msg: String = ""
        var arr: ArrayList<String> = arrayListOf()
        manager.commands.forEach(){
            var str = "${it.cmd} - ${it.info} \n"
            msg = (msg + str)
        }
        return ServerMessage(msg)
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }
}