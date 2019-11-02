package com.example.chatclientcat


import kotlinx.serialization.json.Json
import java.io.PrintStream
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList

object ServerConnector : Runnable, Observable{

    lateinit var  socket: Socket
    private lateinit var outStream: PrintStream
    private lateinit var inStream: Scanner
    private lateinit var parsedMessage: ChatMessage
    var userName = ""
    val messageList = ArrayList<Any>()
    private val observerList = ArrayList<Observer>()

    override fun run() {

        try {
            socket = Socket("192.168.2.254", 3000)
            outStream = PrintStream(socket.getOutputStream(), true)
            inStream = Scanner(socket.getInputStream())
            while (inStream.hasNextLine()){

                val read = inStream.nextLine()

                parsedMessage = Json.parse(ChatMessage.serializer(), read)
                messageList.add(parsedMessage)
                notifyObserver()


            }
        }
        catch (e: Exception) { println(e.message) }

    }

    override fun registerObserver(observer:Observer) {
        observerList.add(observer)
    }

    override fun deregisterObserver(observer: Observer) {
        observerList.remove(observer)
    }
    override fun notifyObserver() {
        observerList.forEach{ it.updateObserver(messageList) }
    }


    fun sendMessage(message: ChatMessage){

        outStream.println(Json.stringify(ChatMessage.serializer(), message))
    }

    fun verifyUserName(newUserName: String): Boolean {
        val messageToServer = ChatMessage(textMessage = "Hello",userName = newUserName)
        sendMessage(messageToServer)
        
         if (parsedMessage.textMessage == "welcome $newUserName") {
            userName = newUserName; return true
         } else return false

    }


}