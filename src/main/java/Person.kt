import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Класс, являющийся полем класса Ticket.
 */

class Person: java.io.Serializable {
    @JsonProperty("weight")
    private var pheight: Float = 0.0F
    @JsonProperty("height")
    private var pweight: Double? = 0.0
    @JsonProperty("location")
    @JsonDeserialize(`as` = Location::class)
    private var plocation: Location? = Location(1, 1F, 1F, "")
    constructor(height: Float, weight: Double?, location: Location?){
        this.pweight = weight
        this.pheight = height
        this.plocation = location
    }
    constructor(){

    }
    var location: Location? = Location(1,1F,1F,"")
        get() = this.plocation

    /**
     * Метод, отвечающий за отображение объекта класса в строке.
     */
    override fun toString(): String {
        return "Person(height=$pheight, weight=$pweight, location=${plocation.toString()})"
    }
}