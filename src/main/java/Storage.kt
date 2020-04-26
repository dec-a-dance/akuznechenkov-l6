import commands.AbstractCommand
import commands.CommandManager
import commands.TicketComparator
import java.util.*
import kotlin.collections.HashSet

class Storage {

    private var userfilename = String()
    private var buf_id: Long
    private var scriptWorks: Boolean
    private var scan = Scanner(System.`in`)
    private var collection: HashSet<Ticket>
    private var tcomp = TicketComparator()
    private var arr = listOf<String>()
    private var commands = hashSetOf<AbstractCommand>()
    var puserfilename = String()
        get() = this.userfilename
    var pbuf_id = 0L
        get() = this.buf_id
    var pscriptWorks: Boolean = false
        get() = this.scriptWorks
    var pscan = Scanner(System.`in`)
        get() = this.scan
    var pcollection = HashSet<Ticket>()
        get() = this.collection
    var ptcomp = TicketComparator()
        get() = this.tcomp
    var parr: List<String> = listOf()
        get() = this.arr
    var pcommands: HashSet<AbstractCommand> = hashSetOf()
        get() = this.commands
    constructor(userfilename: String, buf_id: Long, scriptWorks: Boolean, scan: Scanner, collection: HashSet<Ticket>, tcomp: TicketComparator, commands: HashSet<AbstractCommand>, arr: List<String>){
        this.userfilename = userfilename
        this.buf_id = buf_id
        this.scriptWorks = scriptWorks
        this.scan = scan
        this.commands = commands
        this.collection = collection
        this.tcomp = tcomp
        this.arr = arr
    }
    fun setFileName(userfilename: String){
        this.userfilename = userfilename
    }
    fun setBufId(buf_id: Long){
        this.buf_id = buf_id
    }
    fun setScriptWorks(scriptWorks: Boolean){
        this.scriptWorks = scriptWorks
    }
    fun setScan(scan: Scanner){
        this.scan = scan
    }
    fun setCollection(collection: HashSet<Ticket>){
        this.collection = collection
    }
    fun setArr(arr: List<String>){
        this.arr = arr
    }
    fun setComm(comm: HashSet<AbstractCommand>){
        this.commands = comm
    }
}