// Users allows only a single instance of itself. It registers the usernames of all connected users in a HashSet collection.
//when a user exits the server, the username is deRegistered and can be used again by a new user.
// when the USER command is called from the client, User outputs the a list of all active users.
object  Users {
     val users = HashSet<String>()
     fun deRegisterUserName(userName: String ) {
          users.remove(userName)
    }

     fun registerUserName(userName: String) {
         this.checkIfUsernameExits(userName)
    }
    private fun checkIfUsernameExits(userName: String){
        if (!users.contains(userName)) users.add(userName)
    }

    override fun toString(): String {
        var userNames = "\nONLINE USERS\n"
        users.forEach{ userNames += "\n$it\r\n"}
        return userNames
    }
}