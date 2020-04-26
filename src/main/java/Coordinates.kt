import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Класс, являющийся полем класса Ticket.
 */
public class Coordinates: java.io.Serializable {
    @JsonProperty("x")
    private var cx: Long = 0
    @JsonProperty("y")
    private var cy: Double = 0.0
    constructor(x: Long, y: Double){
        this.cx = x
        this.cy = y
    }
    constructor(){

    }

    /**
     * Метод, отвечающий за отображение объекта класса.
     */
    override fun toString(): String {
        return "Coordinates(x=$cx, y=$cy)"
    }
    val y: Double
        get() = this.cy
}