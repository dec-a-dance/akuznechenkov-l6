package commands

import DatabaseManager
import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
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
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager) : ServerMessage {
        var id = arg.toLong()
        var msg = ""
            val connection: Connection = DriverManager.getConnection(
                "jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require",
                "${databaseManager.USER}",
                "${databaseManager.PASSWORD}"
            )

            val stm = connection.createStatement()
            var rs = stm.executeQuery("SELECT * FROM tickets where ticket_id = $id")
            try {
                rs.next()
                var getUser = rs.getString("creator")
                if (getUser == databaseManager.USER) {
                    stm.executeUpdate("UPDATE tickets set ticket_name = '${tick.name}' where ticket_id=$id ;")
                    stm.executeUpdate("UPDATE tickets set ticket_price = ${tick.price} where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set ticket_type = '${tick.type.toString()}' where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set coordinates_x = ${tick.coordinates.x} where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set coordinates_y = ${tick.coordinates.y} where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set person_height= ${tick.person.height} where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set person_weight = ${tick.person.weight} where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set location_name = '${tick.person.location!!.name}' where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set location_x = '${tick.person.location!!.x}' where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set location_y = '${tick.person.location!!.y}' where ticket_id=$id;")
                    stm.executeUpdate("UPDATE tickets set location_z = '${tick.person.location!!.z}' where ticket_id=$id;")
                    msg = "Элемент с id = $id был обновлен"
                } else {
                    msg = "Элемент с id = $id не принадлежит вам"
                }
            }
            catch(e: SQLException){
                msg = "Элемент с id = $id  не существует"
            }
            stm.close()
            connection.close()
            databaseManager.updateCollection(collection)
            return ServerMessage(msg)
    }

    override fun setTick(t: Ticket){
        this.tick = t
    }
    override fun setArg(a: String){
        this.arg = a
    }
}