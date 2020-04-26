class ServerMessage: java.io.Serializable {
    private var msg: String = ""
    var getMsg : String = ""
    get() = this.msg

    fun setMsg(msg: String){
        this.msg = msg
    }
    constructor(msg: String){
        this.msg = msg
    }
}