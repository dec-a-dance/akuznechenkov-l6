package commands

import DatabaseManager
import ServerMessage
import Ticket
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime


/**
 * Класс, реализующий команду clear, очищающую коллекцию
 */
class Clear(): AbstractCommand() {
    override val cmd = "clear"
    override val info = "очищает коллекцию"
    override val ticket = false
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage {
        val connection: Connection = DriverManager.getConnection("jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require", "${databaseManager.USER}", "${databaseManager.PASSWORD}")
        val stm = connection.createStatement()
        val sql = "DELETE from tickets where (ticket_id > -1) and (creator = '${databaseManager.USER}') ;"
        stm.executeUpdate(sql)
        stm.close()
        connection.close()
        databaseManager.updateCollection(collection)
        return ServerMessage("Коллекция очищена")
    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){

    }
}