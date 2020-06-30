package commands

import DatabaseManager
import ServerMessage
import Storage
import Ticket
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet


/**
 * Класс, реализующий команду show, которая выводит строковое представление всех элементов коллекции
 */
class Show(): AbstractCommand() {
    override val cmd = "show"
    override val info = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage {
        var msg = ""
        collection.forEach{
            msg+= it.toString()
            if (it!=collection.last()){
                msg+= "\n"
            }
        }
        return ServerMessage(msg)
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }
}