import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Класс, являющийся полем класса Ticket.
 */

class Location: java.io.Serializable {
    @JsonProperty("x")
    private var lx: Long = 0
    @JsonProperty("y")
    private var ly: Float = 0.0F
    @JsonProperty("z")
    private var lz: Float = 0.0F
    @JsonProperty("name")
    private var lname: String = ""
    constructor(x: Long, y: Float, z: Float, name: String){
        this.lx = x
        this.ly = y
        this.lz = z
        this.lname = name
    }
    constructor(){

    }
    val x: Long
         get() = this.lx
    val y: Float
         get() = this.ly
    val z: Float
     get() = this.lz
    val name: String
         get() = this.lname
    /**
     * Метод, отвечающий за отображение объекта в строке.
     */
    override fun toString(): String {
        return "Location(x=$lx, y=$ly, z=$lz, name='$lname')"
    }

}