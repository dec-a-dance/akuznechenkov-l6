/**
 * Класс, являющийся полем класса Ticket.
 */
enum class TicketType: java.io.Serializable {
    VIP(3),
    USUAL(2),
    BUDGETARY(1),
    CHEAP(0);
    private var tvalue: Int
    constructor(value: Int){
        this.tvalue = value
    }
    val value: Int
        get() = this.tvalue
}