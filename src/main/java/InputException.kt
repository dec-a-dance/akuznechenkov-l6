import java.lang.Exception

/**
 * Класс пользовательских исключений, связанных с вводом
 */
class InputException(msg: String): Exception(msg) {
    fun cause(){
        super.cause
    }
}