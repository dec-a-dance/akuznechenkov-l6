package commands

import InputException
import ServerMessage
import Storage
import Ticket

/**
 * Класс, реализующий команду add_if_max, добавляющую элемент в коллекцию, если его цена будет максимальной
 */
class AddIfMax(): AbstractCommand() {
    override val cmd = "add_if_max"
    override val info = "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"
    override val ticket = true
    private var tick = Ticket()
    override val par = false
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        var msg: String
        if (collection.maxBy{it.price}!!.price < tick.price){
            var id = collection.maxBy{it.id}!!.id + 1
            tick.setId(id)
            collection.add(tick)
            msg = "Элемент добавлен в коллекцию, ему присвоен id = $id"
            collection.add(tick)
        }
        else{
            msg = "Объект не максимален, не был добавлен в коллекцию"
        }
        return ServerMessage(msg)
    }
    override fun setTick(t: Ticket){
        tick = t
    }
    override fun setArg(a: String){

    }
}