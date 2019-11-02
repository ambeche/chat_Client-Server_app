package com.example.chatclientcat

// ChatMessage Parses incoming messages from the server and  convert out streams to Jason strings
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Serializable
class ChatMessage( val textMessage: String,  val userName:String,
                  private val timeStamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm" ))) {

    override fun toString(): String {
        return "@$userName: $textMessage $timeStamp"
    }

}