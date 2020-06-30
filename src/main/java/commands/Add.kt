package commands

import Person
import Ticket
import TicketType
import Coordinates
import DatabaseManager
import Location
import InputException
import ServerMessage
import Storage
import org.postgresql.util.PSQLException
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime

/**
 * Класс, реализующий команду add, доавляющую элемент в коллекцию
 */
class Add constructor(user: String, password: String): AbstractCommand() {
    override val cmd = "add"
    override val info = "добавляет новый элемент в коллекцию"
    private var tick = Ticket()
    override val ticket = true
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager) : ServerMessage {
        val connection: Connection = DriverManager.getConnection("jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require", "${databaseManager.USER}", "${databaseManager.PASSWORD}")
        val stm = connection.createStatement()
        val sql = "INSERT INTO tickets (ticket_name,ticket_price,ticket_type,coordinates_x,coordinates_y,ticket_date,person_height,person_weight,location_name,location_x,location_y,location_z,creator) VALUES ('${tick.name}',${tick.price},'${tick.type.toString()}',${tick.coordinates.x},${tick.coordinates.y},'${LocalDateTime.parse(tick.creationDate).toLocalDate()}',${tick.person.height},${tick.person.weight},'${tick.person.location!!.name}',${tick.person!!.location!!.x},${tick.person!!.location!!.y},${tick.person!!.location!!.z}, '${databaseManager.USER}' );"
        stm.executeUpdate(sql)
        stm.close()
        connection.close()
        databaseManager.updateCollection(collection)
        var msg = "Элемент добавлен в коллекцию"
        return ServerMessage(msg)
    }

    override fun setTick(t: Ticket){
        tick = t
    }
    override fun setArg(a: String){

    }
}