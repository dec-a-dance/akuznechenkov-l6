import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.logging.Logger

class DatabaseManager {
    val URL =
        "jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/defaultdb?sslmode=require"
    var USER = ""
    var PASSWORD = ""
    val logger: Logger = Logger.getLogger("log.txt")

    constructor() {
        try {
            Class.forName("org.postgresql.Driver")
            logger.info("Драйвер подключен")
        } catch (e: ClassNotFoundException) {
            logger.severe("Драйвер для PostgreSQL не найден.")
            e.printStackTrace()
        }
    }

    fun addUser(login: String, password: String): Boolean {
        logger.info("Добавляем нового пользователя в БД.")
        val connection = DriverManager.getConnection(URL, "doadmin", "qn2ja517e2cyc53y")
        try {
            var query: String = "CREATE ROLE $login WITH \n" +
                    "  LOGIN \n" +
                    "  NOSUPERUSER \n" +
                    "  INHERIT\n" +
                    "  NOCREATEDB\n" +
                    "  NOCREATEROLE\n" +
                    "  NOREPLICATION\n" +
                    "  ENCRYPTED PASSWORD '$password';\n" +
                    "GRANT lab7_user TO " + login + ";\n" +
                    "ALTER ROLE $login SET password_encryption TO 'md5';"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.execute()
            USER = login
            PASSWORD = password
            return true
        } catch (e: SQLException) {
            e.printStackTrace()
            logger.severe("Не удалось создать аккаунт, ваш логин $login занят или содержит недопустимые символы")
            return false
        }
    }

    fun login(login: String, password: String) : Boolean {
        try {
            val connection: Connection = DriverManager.getConnection(
                "jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require",
                "$login",
                "$password"
            )
            USER = login
            PASSWORD = password
            connection.close()
            return true
        }
        catch(e: SQLException){
            return false
        }

    }

    fun updateCollection(collection: HashSet<Ticket>) {
        val connection: Connection = DriverManager.getConnection(
            "jdbc:postgresql://lab-prog-database-do-user-7323038-0.a.db.ondigitalocean.com:25060/Lab7?sslmode=require",
            "$USER",
            "$PASSWORD"
        )
        collection.clear()
        val stm = connection.createStatement()
        val rs = stm.executeQuery("SELECT * FROM tickets;")
        while (rs.next()) {
            var id = rs.getLong("ticket_id")
            var date = rs.getString("ticket_date")
            var name = rs.getString("ticket_name")
            var price = rs.getLong("ticket_price")
            var strType = rs.getString("ticket_type")
            var type = when (strType){
                "VIP" -> TicketType.VIP
                "USUAL" -> TicketType.USUAL
                "BUDGETARY" -> TicketType.BUDGETARY
                "CHEAP" -> TicketType.CHEAP
                else -> null
            }
            var coordX = rs.getLong("coordinates_x")
            var coordY = rs.getDouble("coordinates_y")
            var personHeight = rs.getFloat("person_height")
            var personWeight = rs.getDouble("person_weight")
            var locationName = rs.getString("location_name")
            var locationX = rs.getLong("location_x")
            var locationY = rs.getFloat("location_y")
            var locationZ = rs.getFloat("location_z")
            collection.add(Ticket(id,name,Coordinates(coordX, coordY),date,price,type,Person(personHeight, personWeight, Location(locationX, locationY, locationZ, locationName))))

        }
        stm.close()
        connection.close()
    }
}