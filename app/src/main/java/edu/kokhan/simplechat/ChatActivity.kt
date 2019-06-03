package edu.kokhan.simplechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val messagesRef = database.getReference("messages")
    private val MAX_MESSAGE_LENGTH = 666

    val messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
//        messagesRecyclerView.scrollToPosition(messages.size)//scrollToPosition(messages.size)
        val adapter = MessagesAdapter(this, messages)
        setupListeners(adapter)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        messagesRecyclerView.layoutManager = layoutManager
        messagesRecyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_signout) onBackPressed()
        return true
    }

    private fun messageIsCorrect(message: String): Boolean {
        var correctResult = true
        if (message == "") {
            Toast.makeText(this, "Message input is empty!", Toast.LENGTH_SHORT).show()
            correctResult = false
        }

        if (message.length > MAX_MESSAGE_LENGTH) {
            Toast.makeText(
                this,
                "Message text cannot contains more than $MAX_MESSAGE_LENGTH symbols!",
                Toast.LENGTH_SHORT
            ).show()
            correctResult = false
        }

        return correctResult
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

    private fun setupListeners(adapter: MessagesAdapter){
        val username = intent.getStringExtra("USERNAME")

        sendMessageButton.setOnClickListener {
            val message = editTextMessage.text.toString()

            if (!messageIsCorrect(message)) return@setOnClickListener

            messagesRef.push().setValue(Message(message, username))
            editTextMessage.setText("")
        }

        messagesRef.addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val element = dataSnapshot.getValue(Message::class.java)
                    messages.add(element!!)
                    adapter.notifyDataSetChanged()
                    messagesRecyclerView.smoothScrollToPosition(messages.size)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                }
            }
        )
    }
}
