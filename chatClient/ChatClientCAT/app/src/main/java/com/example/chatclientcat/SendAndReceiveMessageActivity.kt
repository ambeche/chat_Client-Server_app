package com.example.chatclientcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_send_and_receive_message.*
import java.lang.Exception

// UI for chatting
class SendAndReceiveMessageActivity : AppCompatActivity(), Observer {

    private val data =  ServerConnector.messageList
    private lateinit var chatAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_and_receive_message)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(this, data)
        recyclerView.adapter = chatAdapter
        ServerConnector.registerObserver(observer = this)

        // when button is clicked, it starts a background Thread that sends users massages to server
        sendButton.setOnClickListener {
            Thread(Runnable {
                if (messageView.text.isNotEmpty()) {

                    try {
                        val message = ChatMessage(messageView.text.toString(),ServerConnector.userName)
                        ServerConnector.sendMessage(message)
                        data.add(message.toString())
                        messageView.text.clear()
                        chatAdapter.notifyDataSetChanged()

                    }
                    catch (e: Exception){println(e.message)}
                }
            }).start()
        }
    }

    // creates and option menu with items; Users, Top Chatters and Chat History
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.commands_to_server, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection from option Menu
        return when (item.itemId) {
            R.id.users -> { commandsToServer(command = "users"); true }

            R.id.topChatters -> { commandsToServer(command = "topchatters"); true }

            R.id.chatHistory -> { commandsToServer(command = "chathistory"); true }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateObserver(chatMessage: List<Any>) {
      // updates my RecyclerView.Adapter, ChatAdapter
        chatAdapter.notifyDataSetChanged()
    }

    // Starts a background Thread that interprets user commands
    private fun commandsToServer(command:String){
        Thread(Runnable {

            try {
                val message = ChatMessage(command,ServerConnector.userName)
                ServerConnector.sendMessage(message)
            }
            catch (e: Exception){println(e.message)}
        }).start()
    }

    override fun onStop() {
        super.onStop()
        ServerConnector.socket.close()
        Log.d("stopped","ending connection to server")
    }
}
