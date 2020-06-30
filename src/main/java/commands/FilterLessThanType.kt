package commands

import DatabaseManager
import InputException
import ServerMessage
import Storage
import Ticket
import TicketType
import java.lang.NumberFormatException
import java.sql.Connection
import java.sql.DriverManager

/**
 * Класс, реализующий команду filter_less_than_type, отображающие элементы, у которых значение типа меньше, чем заданное
 */
class FilterLessThanType() : AbstractCommand() {
    override val cmd = "filter_less_than_type"
    override val info = "вывести элементы, значение поля type которых, меньше чем заданное "
    override val ticket = false
    private var arg = ""
    override val par = true
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>,databaseManager: DatabaseManager): ServerMessage{
        var wasFound: Boolean = false
        var msg = ""
        try {
            var searchType: TicketType = when (arg) {
                "VIP" -> TicketType.VIP
                "USUAL" -> TicketType.USUAL
                "BUDGETARY" -> TicketType.BUDGETARY
                "CHEAP" -> TicketType.CHEAP
                else -> throw InputException("")
            }
            val connection: Connection = DriverManager.getConnection("jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require", "${databaseManager.USER}", "${databaseManager.PASSWORD}")
            val stm = connection.createStatement()
            var rs = stm.executeQuery("SELECT * FROM tickets")
            while (rs.next()){
                var id = rs.getLong("ticket_id")
                var name = rs.getString("ticket_name")
                var price = rs.getLong("ticket_price")
                var type = rs.getString("ticket_type")
                var typeOf: TicketType = when (type) {
                    "VIP" -> TicketType.VIP
                    "USUAL" -> TicketType.USUAL
                    "BUDGETARY" -> TicketType.BUDGETARY
                    "CHEAP" -> TicketType.CHEAP
                    else -> throw InputException("")
                }
                var coordX = rs.getLong("coordinates_x")
                var coordY = rs.getFloat("coordinates_y")
                var personHeight = rs.getFloat("person_height")
                var personWeight = rs.getFloat("person_weight")
                var locationName = rs.getString("location_name")
                var locationX = rs.getLong("location_x")
                var locationY = rs.getFloat("location_y")
                var locationZ = rs.getFloat("location_z")
                if(typeOf<searchType) {
                    msg += "Ticket id = $id, name = '$name', price = $price, type = $type, X-coordinate = $coordX, Y-coordinate = $coordY, person height = $personHeight, person weight = $personWeight, location name = $locationName, location X-coordinate = $locationX, location Y-coordinate = $locationY, location Z-coordinate = $locationZ \n"
                }
            }
            stm.close()
            connection.close()
        }
        catch(e: InputException){
            msg = "Формат аргумента неверен"
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){
        this.arg = a
    }

}