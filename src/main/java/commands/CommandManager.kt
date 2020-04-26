package commands

import kotlin.collections.HashSet

/**
 * Класс. реализующий объект, управляющий всеми командами.
 */
class CommandManager{
    //var stor = Storage("collection.json", 1L, false, Scanner(System.`in`), hashSetOf(), TicketComparator(), hashSetOf(), listOf())
    //var console = InputConsole(stor)
    val add: Add = Add()
    val clear: Clear = Clear()
    val exit = Exit()
    val filterByPrice = FilterByPrice()
    val filterLessThanType = FilterLessThanType()
    val info = Info()
    val removeById = RemoveById()
    val show = Show()
    val update = Update()
    val help = Help(this)
    val addIfMax = AddIfMax()
    val addIfMin = AddIfMin()
    val executeScript = ExecuteScript(this)
    val removeLower = RemoveLower()
    val printAscending = PrintAscending()
    var commands: HashSet<AbstractCommand> = hashSetOf(add, addIfMax, clear, addIfMin, removeLower, exit, executeScript, help, printAscending, filterByPrice,  filterLessThanType, info, removeById, show, update)
   // constructor(console: InputConsole){
  //      this.console = console
  //  }
    constructor(){

    }
}