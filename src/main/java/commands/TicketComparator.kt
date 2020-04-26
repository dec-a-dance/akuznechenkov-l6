package commands
import Ticket
/**
 * Класс, реализующий сравнение билетов по цене
 */
class TicketComparator: Comparator<Ticket> {
    override fun compare(t1: Ticket, t2: Ticket): Int {
        return (t1.price - t2.price).toInt()
    }

}