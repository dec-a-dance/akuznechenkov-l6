import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Класс, являющийся полем класса Ticket.
 */

data class Location(var x: Long = 0, var y: Float = 0.0F, var z: Float = 0.0F, var name: String = ""): java.io.Serializable