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
 * Класс, реализующий команду remove_lower, которая удаляет все элементы, цена которых меньше, чем у заданного
 */
class RemoveLower(): AbstractCommand() {
    override val cmd = "remove_lower"
    override val info = "убрать все элементы, значение которых меньше, чем у входящего"
    val logger = Logger.getLogger("log.txt")
    override val ticket = false
    private var arg = ""
    override val par = true
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage{
        var wasFound: Boolean = false
        var msg = ""
        try {
            var searchPrice: Long = arg.toLong()
            if(collection.removeIf{it.price<searchPrice}){
                val connection: Connection = DriverManager.getConnection("jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require", "doadmin", "qn2ja517e2cyc53y")
                val stm = connection.createStatement()
                val sql = "DELETE from tickets where (ticket_price < $searchPrice) and (creator = '${databaseManager.USER}')"
                stm.executeUpdate(sql)
                var before = collection.size
                databaseManager.updateCollection(collection)
                if (collection.size!=before) {
                    msg = "Элементы с ценой нижу $searchPrice был удален из базы данных"
                }
                else{   msg = "Элементы с ценой ниже $searchPrice не были найдены или не принадлежат вам" }

            }
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