package commands


import Coordinates
import InputException
import Location
import Person
import ServerMessage
import Storage
import Ticket
import TicketType
import java.io.EOFException
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.IndexOutOfBoundsException
import java.lang.System.`in`
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Logger
import kotlin.NoSuchElementException

/**
 * Класс, реализующий команду execute_script, которая исполняет скрипт, записанный в файле, указанном пользователем
 */
class ExecuteScript(gmanager: CommandManager): AbstractCommand() {
    val logger = Logger.getLogger("log.txt")
    override val cmd = "execute_script"
    override val info = "cчитать и исполнить скрипт из указанного файла"
    override val ticket = false
    override val par = true
    var msg = ""
    var scriptWorks = true

    var manager = gmanager
    private var arg = ""
    /**
     * Метод, отвечающий за выполнение команды
     */
    override fun execute(collection: HashSet<Ticket>): ServerMessage{
        try {
            val input = FileInputStream(arg)
            val scan = Scanner(input)
            logger.info("Начали исполнение скрипта из файла $arg")
            while (scriptWorks) {
                try {
                    var line = scan.nextLine()
                    var arr = line!!.trim().split("\\s+".toRegex())
                    var wasFound = false
                    manager.commands.forEach() {
                        if (arr[0] == it.cmd) {
                            if (it.ticket) {
                                it.setTick(readTicket(scan))
                            }
                            if (it.par) {
                                if (arr.size == 2) {
                                    it.setArg(arr[1])
                                } else {
                                    scriptWorks = false
                                    error("недостаточно агрументов")
                                }
                            }
                            if (scriptWorks) {
                                it.execute(collection)
                                wasFound = true
                            }
                        } else {
                            if (it.equals(collection.last()) and (!wasFound)) {
                                scriptWorks = false
                                msg = "Получена несуществующая команда, работа скрипта закончена"
                            }
                        }
                    }
                }
                catch(e: EOFException){
                    scriptWorks = false
                    msg = "Файл был успешно считан и исполнен"
                    logger.info("Исполнение скрипта выполнено успешно")
                }
            }

        }
        catch(e: FileNotFoundException){
            msg = "Файл не был найден"
            logger.info("Выбранного пользователем файла не существует")
        }
        return ServerMessage(msg)
    }
    override fun setArg(a: String) {
        this.arg = a
    }
    override fun setTick(t: Ticket) {
    }

    fun readTicket(scan: Scanner) : Ticket {
        var name: String = ""
        var coordinates: Coordinates
        var creationDate: String = LocalDateTime.now().toString()
        //var creationDate: String = "${LocalDateTime.now().dayOfMonth} ${LocalDateTime.now().month} ${LocalDateTime.now().year}"
        var price: Long = 0L
        var type: TicketType? = null
        while (true) {
            try {
                name = scan.nextLine() ?: throw InputException("Строка не может быть null")
                if (name == "") {
                    throw InputException("Строка не может быть пустой")
                }
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        var strx: String
        var x: Long = 0L
        while (true) {
            print("Введите координату по Х: ")
            try {
                strx = scan.nextLine() ?: throw InputException("Строка не подходит по формату")
                x = strx.toLong()
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: java.lang.NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        var stry: String
        var y: Double = 0.0
        while(true) {
            print("Введите координату по Y: ")
            try {
                stry = scan.nextLine() ?: throw InputException("Число не подходит по формату")
                y = stry.toDouble()
                if (y < -103) {
                    throw InputException("Число не входит в заданный диапазон")
                }
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        coordinates = Coordinates(x, y)
        var strprice: String
        while (true) {
            print("Введите цену: ")
            try {
                strprice =
                    scan.nextLine() ?: throw InputException("Введенная строка не подходит по формату, повторите ввод")
                price = strprice.toLong()
                if (price < 0) {
                    throw InputException("Числовое значение некорректно, поторите ввод")
                }
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        var noType = true
        while (noType) {
            print("Введите тип билета (VIP, USUAL, BUDGETARY, CHEAP): ")
            var strtype: String? = scan.nextLine()
            var eType = InputException("То, что вы ввели, не является типом билета, повторите ввод")
            when (strtype) {
                "VIP" -> {
                    type = TicketType.VIP
                    noType = false
                }
                "USUAL" -> {
                    type = TicketType.USUAL
                    noType = false
                }
                "BUDGETARY" -> {
                    type = TicketType.BUDGETARY
                    noType = false
                }
                "CHEAP" -> {
                    type = TicketType.CHEAP
                    noType = false
                }
                null -> {
                    type = null
                    noType = false
                }
                "" -> {
                    type = null
                    noType = false
                }
                else -> {
                    println(eType)
                    scriptWorks = false
                    noType = false
                }
            }
        }
        var locx: Long = 0L
        var locy: Float = 0.0F
        var locz: Float = 0.0F
        while (true) {
            print("Введите координату по Х: ")
            try {
                var strlocx: String = scan.nextLine() ?: throw InputException("Некорректное значение")
                locx = strlocx.toLong()
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        while (true) {
            print("Введите координату по Y: ")
            try {
                var strlocy: String = scan.nextLine() ?: throw InputException("Некорректное значение")
                locy = strlocy.toFloat()
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        while (true) {
            print("Введите координату по Z: ")
            try {
                var strlocz: String = scan.nextLine() ?: throw InputException("Некорректное значение")
                locz = strlocz.toFloat()
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        var locname: String = ""
        while (true) {
            print("Введите название локации: ")
            try {
                locname = scan.nextLine() ?: throw InputException("Введена пустая строка")
                if (locname.length > 852) {
                    throw InputException("Слишком длинная строка, повторите ввод")
                }
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        val location = Location(locx, locy, locz, locname)
        var height: Float = 0.0F
        while (true) {
            print("Введите рост человека: ")
            try {
                var strheight: String = scan.nextLine() ?: throw InputException("Получена пустая строка, повторите ввод")
                height = strheight.toFloat()
                if (height <= 0.0) {
                    throw InputException("Число не подходит по формату")
                }
                break
            } catch (e: NumberFormatException) {
                println(e)
            } catch (e: InputException) {
                println(e)
            }
        }
        var weight: Double? = 0.0
        while (true) {
            print("Введите вес человека: ")
            try {
                var strweight: String =
                    scan.nextLine() ?: throw InputException("Введенная вами строка не соотвествует формату")
                weight = strweight.toDouble()
                if (weight < 0.0) {
                    throw InputException("Введенная вами строка не соотвествует формату")
                }
                break
            } catch (e: InputException) {
                println(e)
                scriptWorks = false
                break
            } catch (e: NumberFormatException) {
                println(e)
                scriptWorks = false
                break
            }
        }
        val person: Person = Person(height, weight, location)
        val newTicket: Ticket = Ticket(0, name, coordinates, creationDate, price, type, person)
        return newTicket
    }
}