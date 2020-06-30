package commands

import DatabaseManager
import ServerMessage
import Storage
import Ticket
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

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
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage {
        return ServerMessage("Сейчас вам доступно ${collection.size} элементов " +
                "Вы используете базу данных под логином ${databaseManager.USER}\n")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }

}