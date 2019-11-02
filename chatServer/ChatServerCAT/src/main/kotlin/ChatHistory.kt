// ChatHistory allows only a single instance of itself to be created, it implements interface Observable and can register or deregister objects of type Observer
// When Observer, ChatConnector instance sends a new message, the message is added to chathistory List collection, and the insert() method of ChatHistory call the
// updateObserver method on all its Observers, and the message is pushed to all connected clients.
object ChatHistory: Observable {

    val chatHistory: MutableList<ChatMessage> = mutableListOf()
    private val observers: MutableList<Observer> = mutableListOf()

    fun insert(message: ChatMessage){
        val commands = arrayOf("USERS", "CHATHISTORY", "TOPCHATTERS", "EXIT")
        if (!commands.contains(message.textMessage.toUpperCase())){
            chatHistory.add(message)
            observers.forEach { if(it.getUser() != message.userName) it.updateObserver(message)}
        }
    }

    override fun registerObserver(observer: Observer) { observers.add(observer) }

    override fun deRegisterObserver(observer: Observer) {observers.remove(observer)}

    override fun toString(): String {
        var conversations = "\nCONVERSATION HISTORY\n"
        chatHistory.forEach { conversations += "\n$it\r\n" }
        return conversations
    }
}