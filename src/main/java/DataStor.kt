import commands.CommandManager
import java.net.InetAddress

data class DataStor(var name: String = "", var par: String = "", var tick: Ticket = Ticket(), var login: String = "", var password: String = ""): java.io.Serializable{
    fun unpack(collection: HashSet<Ticket>, databaseManager: DatabaseManager) : ServerMessage {
        val manager = CommandManager()
        var message = ServerMessage("")
        manager.commands.forEach() {
            if (it.cmd == this.name) {
                if (it.ticket) {
                    it.setTick(this.tick)
                    println()
                }
                if (it.par) {
                    it.setArg(this.par)
                }
                databaseManager.USER = login
                databaseManager.PASSWORD = password
                message = it.execute(collection, databaseManager)
            }

        }
        return message
    }
}