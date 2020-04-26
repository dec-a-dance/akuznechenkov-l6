package commands

import InputException
import ServerMessage
import Storage
import Ticket
/**
 * Класс, реализующий команду add_if_min, добавляющую элемент в коллекцию, если его цена будет минимальной
 */
class AddIfMin(): AbstractCommand() {
    override val cmd = "add_if_min"
    override val info = "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"
    override val ticket = true
    override val par = false
    private var tick = Ticket()
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var msg: String
        if (collection.minBy{it.price}!!.price > tick.price){
            var id = collection.maxBy{it.id}!!.id + 1
            tick.setId(id)
            collection.add(tick)
            msg = "Элемент добавлен в коллекцию, ему присвоен id = $id"
            collection.add(tick)
        }
        else{
            msg = "Объект не минимален, не был добавлен в коллекцию"
        }
        return ServerMessage(msg)
    }
    override fun setTick(t: Ticket){
        tick = t
    }
    override fun setArg(a: String){

    }
}