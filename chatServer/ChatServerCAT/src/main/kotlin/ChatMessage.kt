// ChatMessage deSerializes all incoming messages and transform them into ChatMessage object
// The updateObserver method of Observers call the toString method of ChatMessage, which renders the string with the Username and time stamp included.
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Serializable
class ChatMessage(val textMessage: String, val userName:String, private val timeStamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm" ))) {

    override fun toString(): String {
        return "@$userName: $textMessage\t$timeStamp"
    }

}



