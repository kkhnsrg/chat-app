package edu.kokhan.simplechat.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import edu.kokhan.simplechat.R
import edu.kokhan.simplechat.model.Message
import edu.kokhan.simplechat.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : AppCompatActivity(), ChatPresenter.View {

    private val messages = ArrayList<Message>()
    private val presenter = ChatPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupListeners()

        refreshCurrentChatList(messages)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        messagesRecyclerView.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_signout) onBackPressed()
        return true
    }

    override fun onBackPressed() {
        logoutAlert()
    }

    private fun logoutAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Logout")
            setMessage("Are you sure?")
            setPositiveButton(android.R.string.yes) { _, _ -> finish() }
            setNegativeButton(android.R.string.no) { dialog, _ -> dialog.cancel() }
        }
        builder.show()
    }

    private fun setupListeners() {
        val username = intent.getStringExtra("USERNAME")
        sendMessageButton.setOnClickListener {
            val message = editTextMessage.text.toString()
            presenter.sendNewMessage(message, username)
        }
    }

    override fun refreshCurrentChatList(currentChatMessage: ArrayList<Message>) {
        val chatListAdapter = MessagesAdapter(this, currentChatMessage)
        messagesRecyclerView.adapter = chatListAdapter
    }

    override fun showEmptyInputMessage() {
        Toast.makeText(this, "Message input is empty!", Toast.LENGTH_SHORT).show()
    }

    override fun showLargeInputMessage() {
        Toast.makeText(
            this,
            "Message text cannot contains more than ${presenter.MAX_MESSAGE_LENGTH} symbols!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun clearMessageInput() {
        editTextMessage.setText("")
    }
}
