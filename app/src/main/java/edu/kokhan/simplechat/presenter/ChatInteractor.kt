package edu.kokhan.simplechat.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.kokhan.simplechat.model.Message

internal class ChatInteractor(private val presenter: ChatPresenter) {

    private val database = FirebaseDatabase.getInstance()
    private val messagesRef = database.getReference("messages")
    private val currentChatList = ArrayList<Message>()

    init {
        retrieveCurrentChat()
    }

    private fun retrieveCurrentChat() {
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentChatList.clear()
                for (messagesSnapshot in dataSnapshot.children) {
                    val chatMessage = messagesSnapshot.getValue<Message>(Message::class.java)
                    currentChatList.add(chatMessage!!)
                }
                presenter.refreshCurrentChatList(currentChatList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun sendNewMessageToChat(message: Message) {
        messagesRef.push().setValue(message)
    }
}