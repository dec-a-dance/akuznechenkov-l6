import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Класс, являющийся полем класса Ticket.
 */

data class Person(var height: Float = 0.0F, var weight: Double? = 0.0, var location: Location?): java.io.Serializable