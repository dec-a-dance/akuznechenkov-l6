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
 * Класс, реализующий команду filter_by_price, которая выводит элементы, у которых цена равна заданной юзером
 */
class FilterByPrice() : AbstractCommand() {
    override val cmd = "filter_by_price"
    override val info = "вывести элементы, значения поля  price которых равно заданному"
    override val ticket = false
    private var arg = ""
    override val par = true
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>, databaseManager: DatabaseManager): ServerMessage{
        var msg = ""
        try {
            var searchPrice: Long = arg.toLong()
            val connection: Connection = DriverManager.getConnection("jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require", "${databaseManager.USER}", "${databaseManager.PASSWORD}")
            val stm = connection.createStatement()
            var rs = stm.executeQuery("SELECT * FROM tickets where ticket_price = $searchPrice")
            while (rs.next()){
                var id = rs.getLong("ticket_id")
                var name = rs.getString("ticket_name")
                var price = rs.getLong("ticket_price")
                var type = rs.getString("ticket_type")
                var searchType: TicketType = when (type) {
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
                    msg += "Ticket id = $id, name = '$name', price = $price, type = $type, X-coordinate = $coordX, Y-coordinate = $coordY, person height = $personHeight, person weight = $personWeight, location name = $locationName, location X-coordinate = $locationX, location Y-coordinate = $locationY, location Z-coordinate = $locationZ \n"
            }
            stm.close()
            connection.close()
        }
        catch(e: NumberFormatException){
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