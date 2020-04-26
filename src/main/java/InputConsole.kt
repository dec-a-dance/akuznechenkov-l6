import com.fasterxml.jackson.databind.ObjectMapper
import commands.CommandManager
import java.lang.System.`in`
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.time.LocalDateTime

/**
 * Класс, реализующий действия консоли
 */
class InputConsole {
    val ADDR = "161.35.104.236"
    val PORT = 4334
    var consoleWorks: Boolean = true
    val serializator = Serializator()

    val e = InputException("Введенная вами строка не является командой, введите help чтобы получить список всех команд")
    var line: String? = null
    val manager = CommandManager()
    var commands = manager.commands
    /**
     * Метод, реализующий считывание строки и определение, является ли строка командой, а также исполнение этой команды.
     */
    fun read() {
        var adress: SocketAddress = InetSocketAddress(ADDR, PORT)
        var channel = DatagramChannel.open()
        channel.connect(adress)
        println("Подключены к $ADDR по порту $PORT")
        while (true) {
            var tick: Ticket
            var inputBuffer = ByteBuffer.allocate(100000)
            val mapper = ObjectMapper()
            var noCommand = true
            var noAnswer = true
            print("Введите команду: ")
            line = readLine()
            var arr = line!!.trim().split("\\s+".toRegex())

            commands.forEach() {
                if (arr[0] == it.cmd) {
                    println("полученная команда верна")
                    var dataStor = DataStor()
                    dataStor.setName(arr[0])
                    if (it.par) {
                        dataStor.setPar(arr[1])
                    }
                    if (it.ticket) {
                        tick = readTicket()
                        dataStor.setTick(tick)
                    }
                    dataStor.setAddr(InetAddress.getLocalHost())
                    var buffer = ByteBuffer.wrap(serializator.serialize(dataStor))
                    channel.send(buffer, adress)
                    while(noAnswer) {
                        inputBuffer.clear()
                        adress = channel.receive(inputBuffer)
                        inputBuffer.flip()
                        var b = inputBuffer.array()
                        if (String(b).trim().isNotBlank()) {
                            val message = serializator.deserialize(b)
                            if (message is ServerMessage) {
                                println(message.getMsg)
                                noAnswer = false
                            }
                        }
                    }
                    noAnswer = true
                    noCommand = false
                } else {
                    try {
                        if (it == commands.last()) {
                            if (noCommand) {
                                throw e
                            }
                        }
                    } catch (e: InputException) {
                        println(e)
                    }
                }
            }



            if (arr[0] == "exit") {
                break
            }
        }
    }


    /**
     * Метод, осуществлящий работу с входящим файлом
     */

    fun readTicket() : Ticket {
        var name: String
        var coordinates: Coordinates
        var creationDate: String = LocalDateTime.now().toString()
        //var creationDate: String = "${LocalDateTime.now().dayOfMonth} ${LocalDateTime.now().month} ${LocalDateTime.now().year}"
        var price: Long
        var type: TicketType? = null
        while (true) {
            print("Введите название: ")
            try {
                name = readLine() ?: throw InputException("Строка не может быть null")
                if (name == "") {
                    throw InputException("Строка не может быть пустой")
                }
                break
            } catch (e: InputException) {
                println(e)
            }
        }

        var strx: String
        var x: Long
        while (true) {
            print("Введите координату по Х: ")
            try {
                strx = readLine() ?: throw InputException("Строка не подходит по формату")
                x = strx.toLong()
                break
            } catch (e: InputException) {
                println(e)
            } catch (e: java.lang.NumberFormatException) {
                println(e)
            }
        }
        var stry: String
        var y: Double = 0.0
        while(true) {
                print("Введите координату по Y: ")
                try {
                    stry = readLine() ?: throw InputException("Число не подходит по формату")
                    y = stry.toDouble()
                    if (y < -103) {
                        throw InputException("Число не входит в заданный диапазон")
                    }
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
        }
            coordinates = Coordinates(x, y)
            var strprice: String
            while (true) {
                print("Введите цену: ")
                try {
                    strprice =
                        readLine() ?: throw InputException("Введенная строка не подходит по формату, повторите ввод")
                    price = strprice.toLong()
                    if (price < 0) {
                        throw InputException("Числовое значение некорректно, поторите ввод")
                    }
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
            }
            var noType = true
            while (noType) {
                print("Введите тип билета (VIP, USUAL, BUDGETARY, CHEAP): ")
                var strtype: String? = readLine()
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
                    }
                }
            }
            var locx: Long
            var locy: Float
            var locz: Float
            while (true) {
                print("Введите координату по Х: ")
                try {
                    var strlocx: String = readLine() ?: throw InputException("Некорректное значение")
                    locx = strlocx.toLong()
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
            }
            while (true) {
                print("Введите координату по Y: ")
                try {
                    var strlocy: String = readLine() ?: throw InputException("Некорректное значение")
                    locy = strlocy.toFloat()
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
            }
            while (true) {
                print("Введите координату по Z: ")
                try {
                    var strlocz: String = readLine() ?: throw InputException("Некорректное значение")
                    locz = strlocz.toFloat()
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
            }
            var locname: String
            while (true) {
                print("Введите название локации: ")
                try {
                    locname = readLine() ?: throw InputException("Введена пустая строка")
                    if (locname.length > 852) {
                        throw InputException("Слишком длинная строка, повторите ввод")
                    }
                    break
                } catch (e: InputException) {
                    println(e)
                }
            }
            val location = Location(locx, locy, locz, locname)
            var height: Float
            while (true) {
                print("Введите рост человека: ")
                try {
                    var strheight: String = readLine() ?: throw InputException("Получена пустая строка, повторите ввод")
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
            var weight: Double?
            while (true) {
                print("Введите вес человека: ")
                try {
                    var strweight: String =
                        readLine() ?: throw InputException("Введенная вами строка не соотвествует формату")
                    weight = strweight.toDouble()
                    if (weight < 0.0) {
                        throw InputException("Введенная вами строка не соотвествует формату")
                    }
                    break
                } catch (e: InputException) {
                    println(e)
                } catch (e: NumberFormatException) {
                    println(e)
                }
            }
            val person: Person = Person(height, weight, location)
            val newTicket: Ticket = Ticket(0, name, coordinates, creationDate, price, type, person)
            return newTicket
        }
}