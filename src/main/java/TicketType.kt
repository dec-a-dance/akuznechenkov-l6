/**
 * Класс, являющийся полем класса Ticket.
 */
enum class TicketType(val value: Int): java.io.Serializable {
    VIP(3),
    USUAL(2),
    BUDGETARY(1),
    CHEAP(0);
}