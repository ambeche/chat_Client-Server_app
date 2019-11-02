//TopChatter  implements interface Observer and registers to ChatHistory as an Observer.
// When the TOPCHATTER command is called by a user from a client, TopChatter outputs the list of top chatters
class TopChatter: Observer {

    override fun updateObserver(message: ChatMessage) {
        // prints to console the number of messages sent by each user
        // the grouping method is used to map users to their corresponding number of messages sent.
        ChatHistory.chatHistory.groupingBy { it.userName }.eachCount().toList().sortedByDescending { (_, value) -> value }.toMap().forEach { (t, u) -> println ("$t: = $u messages") }

    }

    override fun getUser(): String {
        // returns a list of top top chatters based on number of messages
        var topChatters = "\nLIST OF TOP CHATTERS\n"
        ChatHistory.chatHistory.groupingBy { it.userName }.eachCount().toList().

            sortedByDescending{ (_, value) -> value }.toMap().forEach { topChatters += "\n$it messages\r\n" }
        return topChatters
    }
}