package edu.kokhan.simplechat.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.content.Intent
import android.widget.Toast
import com.google.firebase.database.*
import edu.kokhan.simplechat.R
import edu.kokhan.simplechat.model.UserCredentials
import java.util.ArrayList


class SignInActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    private val users = ArrayList<UserCredentials>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        usersRef.addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val element = dataSnapshot.getValue(UserCredentials::class.java)
                    users.add(element!!)
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

        signInButton.setOnClickListener {
            var userExist = false
            val currentLoginValue = editTextLogin.text.toString()
            val currentPasswordValue = editTextPass.text.toString()

            //TODO get only one user
            for (user in users) {
                if (user.username == currentLoginValue && user.password == currentPasswordValue)
                    userExist = true
            }

            if (currentLoginValue != "" && currentPasswordValue != "" && userExist) {
                val intent = Intent(baseContext, ChatActivity::class.java)
                intent.putExtra("USERNAME", currentLoginValue)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect credentials!", Toast.LENGTH_SHORT).show()
                clearCredentialsFields()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        clearCredentialsFields()
    }

    private fun clearCredentialsFields(){
        editTextLogin.setText("")
        editTextPass.setText("")
    }

}
