package edu.kokhan.simplechat.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.kokhan.simplechat.R
import edu.kokhan.simplechat.model.Message
import kotlinx.android.synthetic.main.item_message.view.*
import java.util.*

class MessagesAdapter(context: Context, private var messages: ArrayList<Message>) :
    RecyclerView.Adapter<MessagesAdapter.SpecialViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialViewHolder {
        val view = layoutInflater.inflate(R.layout.item_message, parent, false)
        return SpecialViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: SpecialViewHolder, position: Int) {
        val element = messages[position]
        holder.message!!.text = element.textMessage
        holder.name!!.text = element.author
        holder.time!!.text = DateFormat.format("dd.MM.yyy (HH:mm:ss)", element.timeMessage)
    }

    class SpecialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView? = null
        var name: TextView? = null
        var time: TextView? = null

        init {
            message = itemView.messageTextView
            name = itemView.nameTextView
            time = itemView.timeTextView
        }

    }
}