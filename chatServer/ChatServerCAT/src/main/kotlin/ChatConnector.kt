// ChatConnector implements Runnable and Observer interfaces. It registers as an Observer to ChatHistory
// ChatConnector reads all user inputs, parsed them and converts them into ChatMessage objects.
// When its run() method is called, every instance of ChatConnector is a new connection to the server.

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.*



class ChatConnector(inn: InputStream, out: OutputStream):Runnable, Observer {

    private var userName = ""
    private val ins:Scanner = Scanner(inn)
    private val outs: PrintWriter  = PrintWriter(out,true)

    @UnstableDefault
    override fun run() {
        // User instructions , how to call commands
         val commandString = " 1. Send message in this format --> {\"textMessage\" : \"your message/command here\", \"userName\" : \"your username here\"}\r\n " +
                "2. Enter Commands \"USERS\" to check active users\r\n 3. \"CHATHISTORY\"--> checks conversation history\r\n " +
                "4. \"TOPCHATTERS\" --> Checks top 4 chatters \r\n 5. \"EXIT\" --> Logout of chat room"

         //commandParser(serverResponse = commandString)

         while (ins.hasNextLine()) {
            try {

                val messageRead = ins.nextLine()
                val parsedMessage = Json.parse(ChatMessage.serializer(), messageRead) // parsed JSON Strings are converted into ChatMessage object and saved in message variable

                if (parsedMessage.userName.isNotEmpty() && !Users.users.contains(parsedMessage.userName)) {
                    userName = parsedMessage.userName; Users.registerUserName(userName)
                    commandParser(serverResponse = "welcome $userName"); println("$userName logged in"); break
                }
                else commandParser(serverResponse = "Username already in use, Enter a new username")
            }
            catch(e: kotlinx. serialization . json . JsonParsingException){
               commandParser(serverResponse = " Wrong Format, Resend message")
                println(e.message)
            }
            catch(e: kotlinx. serialization . json . JsonUnknownKeyException){
                println(e.message); commandParser(serverResponse = "Message not sent, resend message")
            }
             catch (e: Exception){ println(e.message) }

            }

        loop@ while (ins.hasNextLine()) {
            try {
                val messageRead = ins.nextLine()
                val parsedMessage = Json.parse(ChatMessage.serializer(), messageRead)
                if (parsedMessage.userName == userName) ChatHistory.insert(parsedMessage) // ensures that only registered usernames can send messages

                // Business Logic - Handling of User commands EXIT|USERS|CHATHISTORY|TOPCHATTERS - Input commands are transformed to upper case to render them case insensitive
                when (parsedMessage.textMessage.toUpperCase()) {

                    "EXIT" -> {
                            // logout client from chat room
                            commandParser(serverResponse = "Good by $userName")
                            Users.deRegisterUserName(this.userName)
                            ChatHistory.insert(ChatMessage(textMessage = "Left the Chat room".toUpperCase(), userName = userName))
                            ChatHistory.deRegisterObserver(observer = this); break@loop
                    }

                    "USERS" ->   commandParser(Users.toString())  // outStreams a list of all active users

                    "CHATHISTORY" -> commandParser(ChatHistory.toString())   //outPuts the conversion history to clients

                    "TOPCHATTERS" -> commandParser(TopChatter().getUser())  // outPuts the top chatters based on the number of messages sent
                }

            }
            catch (e: Exception){
                commandParser(serverResponse = " Wrong Format, Resend message")
                println("Exception --> ${e.message}")
            }
        }
    }

   // when called by Singleton ChatHistory, updateObserver pushes message to all connected clients
    override fun updateObserver(message: ChatMessage) {
        outs.println(" ${Json.stringify(ChatMessage.serializer(), message)}")
    }

    // parses command requests from clients and response with the appropriate data
    private fun commandParser(serverResponse: String){
        val response = ChatMessage(textMessage = serverResponse, userName = "Server")
        outs.println(" ${Json.stringify(ChatMessage.serializer(), response)}")
    }

    override fun getUser() = userName
}

