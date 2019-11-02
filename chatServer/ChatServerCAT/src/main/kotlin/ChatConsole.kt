// chatConsole implements Observer, it prints to console all the messages sent by ChatConnector instances
class ChatConsole: Observer {

    override fun updateObserver(message: ChatMessage) {
        // This could still be implemented by printing directly from the updateObserver method of ChatConnector
        ChatHistory.chatHistory.map { println(it) }

    }
    override fun getUser(): String{ return Users.toString()}


}