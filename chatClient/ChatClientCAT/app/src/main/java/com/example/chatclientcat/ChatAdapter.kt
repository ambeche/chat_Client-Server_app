package com.example.chatclientcat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException


class ChatAdapter(private val context: Context, private val data: ArrayList<Any> ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // sets const variables that are used to distinguish Types
    companion object {
        const val TYPE_SENT_MESSAGE = 0
        const val TYPE_RECEIVED_MESSAGE = 1
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // creates ViewHolders as needed based on the type as specified by companion object
        return when (viewType) {
            TYPE_SENT_MESSAGE -> {
                val viewHolder =
                    LayoutInflater.from(context).inflate(R.layout.message, parent, false)
                SentMessageViewHolder(viewHolder)
            }
            TYPE_RECEIVED_MESSAGE -> {
                val viewHolder =
                    LayoutInflater.from(context).inflate(R.layout.received_message, parent, false)
                ReceivedMessageViewHolder(viewHolder)
            }
            else -> throw IllegalArgumentException("Illegal input")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        when (holder) {
            is SentMessageViewHolder -> holder.itemView.findViewById<TextView>(R.id.sentMessage)
                .text = item as String

            is ReceivedMessageViewHolder -> holder.itemView.findViewById<TextView>(R.id.receivedMessage)
                .text = item.toString()
        }

    }

    override fun getItemViewType(position: Int): Int {
        // returns the type for each view item
        return  when(data[position]){
            is String -> TYPE_SENT_MESSAGE
            is ChatMessage -> TYPE_RECEIVED_MESSAGE
            else -> throw IllegalArgumentException("Illegal input")
        }

    }

    // ViewHolder for sent messages, aligned to the right of RecyclerView
    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // ViewHolder for received messages, aligned to the left of RecyclerView
    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

