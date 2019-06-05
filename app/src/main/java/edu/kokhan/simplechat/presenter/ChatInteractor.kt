package edu.kokhan.simplechat.presenter

import com.google.firebase.database.*
import edu.kokhan.simplechat.model.Message
import kotlinx.android.synthetic.main.activity_chat.*


internal class ChatInteractor(private val presenter: ChatPresenter) {

    private val database = FirebaseDatabase.getInstance()
    private val messagesRef = database.getReference("messages")

    private val mCurrentChatList = ArrayList<Message>()

    init {
        retrieveCurrentChat()
    }

    private fun retrieveCurrentChat() {
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mCurrentChatList.clear()

                for (messagesSnapshot in dataSnapshot.children) {
                    val chatMessage = messagesSnapshot.getValue<Message>(Message::class.java)
                    mCurrentChatList.add(chatMessage!!)
                }

                presenter.refreshCurrentChatList(mCurrentChatList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //TODO: Handle error on presenter here.
            }
        })

//        messagesRef.addChildEventListener(
//            object : ChildEventListener {
//                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//                    val element = dataSnapshot.getValue(Message::class.java)
//                    messages.add(element!!)
//                    adapter.notifyDataSetChanged()
//                    messagesRecyclerView.smoothScrollToPosition(messages.size)
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                }
//
//                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
//                }
//
//                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
//                }
//
//                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                }
//            }
//        )
    }

    fun sendNewMessageToChat(message: Message) {
        messagesRef.push().setValue(message)
    }
}