package commands

import DatabaseManager
import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException
import java.sql.Connection
import java.sql.DriverManager
import java.util.logging.Logger

/**
 * Класс, реализующий команду remove_by_id, удаляющую элемент из коллекции по его id
 */
class RemoveById() : AbstractCommand() {
    override val cmd = "remove_by_id"
    override val info = "удалить элемент коллекции по его id"
    override val ticket = false
    val logger = Logger.getLogger("log.txt")
    override val par = true
    private var arg = ""
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage{
        var msg = ""
        try {
                val connection: Connection = DriverManager.getConnection(
                    "jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require",
                    "doadmin",
                    "qn2ja517e2cyc53y"
                )
                val stm = connection.createStatement()
                val sql = "DELETE from tickets where (ticket_id=${arg.toLong()}) and (creator = '${databaseManager.USER}');"
                stm.executeUpdate(sql)
                stm.close()
                connection.close()
            var before = collection.size
            databaseManager.updateCollection(collection)
            if (collection.size!=before) {
                msg = "Элемент с id = $arg был удален из базы данных"
            }
             else{   msg = "Элемент с id = $arg не был найден или не принадлежит вам" }

        }
        catch(e: NumberFormatException){
            msg = "Формат аргумента неверен"
            logger.warning("Пользователь отправил неверный аргумент")
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){
        this.arg = a
    }

}