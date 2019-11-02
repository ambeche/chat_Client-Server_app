package com.example.chatclientcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

// Login Activity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // creates connection to the server
        Thread(ServerConnector).start()

        // Creates and verifies username and starts  Activity, SendAndReceiveMessageActivity using Intent object
        //Runs in the background
        createUsernameButton.setOnClickListener {
            Thread(Runnable {
                if (userNameText.text.isNotEmpty()) {

                    try {
                       val response = ServerConnector.verifyUserName(userNameText.text.toString())
                        userNameText.text.clear()
                        if (response ) {
                            val intent = Intent(this, SendAndReceiveMessageActivity::class.java)
                            startActivity(intent)
                        }

                    }
                    catch (e: Exception){println(e.message)}
                }
            }).start()
        }

    }

    override fun onStop() {
        super.onStop()
        //ServerConnector.socket.close()
        Log.d("stopped","ending connection to server")
    }
}
