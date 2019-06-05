package edu.kokhan.simplechat.presenter;

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.kokhan.simplechat.model.UserCredentials
import java.util.*

class SignInInteractor(private val presenter: SignInPresenter) {

    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    private val users = ArrayList<UserCredentials>()

    init {
        retrieveCurrentChat()
    }

    private fun retrieveCurrentChat() {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (messagesSnapshot in dataSnapshot.children) {
                    val user = messagesSnapshot.getValue<UserCredentials>(UserCredentials::class.java)
                    users.add(user!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun getUsers(): ArrayList<UserCredentials> {
        return users
    }
}
