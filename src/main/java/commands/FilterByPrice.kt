package commands

import InputException
import ServerMessage
import Storage
import Ticket
import java.lang.NumberFormatException

/**
 * Класс, реализующий команду filter_by_price, которая выводит элементы, у которых цена равна заданной юзером
 */
class FilterByPrice() : AbstractCommand() {
    override val cmd = "filter_by_price"
    override val info = "вывести элементы, значения поля  price которых равно заданному"
    override val ticket = false
    private var arg = ""
    override val par = true
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
            var wasFound: Boolean = false
        var msg = ""
        try {
            var searchPrice: Long = arg.toLong()
            collection.forEach {
                if (it.price == searchPrice) {
                    msg.plus(it.toString() + "\n")
                    wasFound = true
                } else {
                    if (it.equals(collection.last()) and (!wasFound)) {
                        msg = "Не было найдено таких элементов"
                    }
                }
            }
        }
        catch(e: NumberFormatException){
            msg = "Формат аргумента неверен"
        }
        return ServerMessage(msg)

    }
    override fun setTick(t: Ticket){

    }
    override fun setArg(a: String){
        this.arg = a
    }

}