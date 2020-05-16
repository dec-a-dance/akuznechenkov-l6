import commands.CommandManager
import java.net.InetAddress

data class DataStor(var name: String = "", var par: String = "", var tick: Ticket = Ticket()): java.io.Serializable{
    fun unpack(collection: HashSet<Ticket>) : ServerMessage {
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
                message = it.execute(collection)
            }

        }
        return message
    }
}