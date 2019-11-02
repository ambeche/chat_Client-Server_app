// ChatServer creates an instance of ServerSocket, listens for new connection and then creates an instance of
// ChatConnector and starts a ChatConnector Thread
import java.net.ServerSocket

class ChatServer {

    fun serve(){

        val serverSocket = ServerSocket(3000,2) // provides endpoint connection with client
        while (true){
            try {
                println("Connecting...")
                val connect = serverSocket.accept() // listens for incoming connections

                val newObserver = ChatConnector(inn = connect.getInputStream(), out = connect.getOutputStream())
                ChatHistory.registerObserver(observer = newObserver)
                ChatHistory.registerObserver(TopChatter())
                ChatHistory.registerObserver(ChatConsole())
                Thread(newObserver).start()  // Starts a ChatConnector thread by calling the run() method on ChatConnector
            }
            catch (e: Exception){ println("Exception --> ${e.message}")}
            finally { println("Connected")}
        }
    }
}

