data class LoginRequest(var command: String, var login: String, var password: String, var res: Boolean): java.io.Serializable{
    fun verify(databaseManager: DatabaseManager){
        if (command == "register"){
            res = databaseManager.addUser(login, password)
        }
        else{
            res = databaseManager.login(login, password)
        }
    }
}