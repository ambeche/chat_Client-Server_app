// Observer is implemented by ChatConnector, ChatConsole and TopChatter
interface Observer {
    fun updateObserver(message: ChatMessage)
    fun getUser(): String
}