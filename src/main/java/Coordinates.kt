import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Класс, являющийся полем класса Ticket.
 */
data class Coordinates(var x: Long = 0L, var y: Double = 0.0): java.io.Serializable