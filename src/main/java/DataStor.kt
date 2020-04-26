import commands.CommandManager
import java.net.InetAddress

class DataStor: java.io.Serializable{
    private var name: String = ""
    private var par: String = ""
    private var tick = Ticket()
    private var addr: InetAddress? = null

    var getName: String = ""
         get() = this.name
    var getPar: String = ""
        get() = this.par
    var getTick: Ticket = Ticket()
        get() = this.tick
    var getAddr: InetAddress? = null
        get() = this.addr
    fun setName(name: String){
        this.name = name
    }
    fun setPar(par: String){
        this.par = par
    }
    fun setTick(tick: Ticket){
        this.tick = tick
    }
    fun setAddr(addr: InetAddress){
        this.addr = addr
    }

    override fun toString(): String {
        return "DataStor(name='$name', par='$par', tick='$tick', addr=$addr)"
    }
    fun unpack(collection: HashSet<Ticket>) : ServerMessage {
        val manager = CommandManager()
        var message = ServerMessage("")
        manager.commands.forEach() {
            if (it.cmd == this.getName) {
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