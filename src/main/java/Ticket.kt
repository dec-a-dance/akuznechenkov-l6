import com.fasterxml.jackson.annotation.JsonAutoDetect
import java.time.ZonedDateTime
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jdk.jfr.DataAmount
import java.time.LocalDateTime

@JsonAutoDetect
@JsonDeserialize(`as` = Ticket::class)
/**
 * Класс, коллекция которого реализуется в программе.
 */
public class Ticket: Comparable<Ticket>, java.io.Serializable {
    @JsonProperty("id")
    private var tid: Long = 0
    @JsonProperty("name")
    private var tname: String = ""
    @JsonProperty("coordinates")
    @JsonDeserialize(`as` = Coordinates::class)
    private var tcoordinates: Coordinates = Coordinates(0,0.0)
    @JsonProperty("creationDate")
    private var tcreationDate: String = LocalDateTime.now().toString()
    @JsonProperty("price")
    private var tprice: Long = 0
    @JsonProperty("type")
    private var ttype: TicketType? = null
    @JsonProperty("person")
    @JsonDeserialize(`as` = Person::class)
    private var tperson: Person = Person(0.0F, 0.0, null)
    @JsonIgnore
    constructor(id: Long, name: String, coordinates: Coordinates, creationDate: String, price: Long, type: TicketType?, person:Person){
        this.tid = id
        this.tname = name
        this.tcoordinates = coordinates
        this.tcreationDate = creationDate
        this.tprice = price
        this.ttype = type
        this.tperson = person
    }
    constructor(){

    }
    val id: Long
         get() = this.tid
    val person: Person
         get() = this.tperson
    val price: Long
         get() = this.tprice
    val type: TicketType?
         get() = this.ttype
    val name: String
         get() = this.tname
    val coordinates: Coordinates
         get() = this.tcoordinates

    override fun compareTo(t: Ticket): Int {
        return (this.id - t.tid).toInt()
    }
    val creationDate: String
        get() = this.tcreationDate
    fun setId(id: Long){
        this.tid = id
    }

    /**
     * Метод, отвечающий за отображение объекта класса в строке.
     */
    override fun toString(): String {
        return "Ticket(id=$tid, name='$tname', coordinates=$tcoordinates, creationDate=$tcreationDate, price=$tprice, type=$ttype, person=$tperson)"
    }



}