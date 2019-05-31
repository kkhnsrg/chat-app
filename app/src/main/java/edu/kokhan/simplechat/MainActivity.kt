package edu.kokhan.simplechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val messagesRef = database.getReference("messages")
    private val MAX_MESSAGE_LENGTH = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val message = editTextMessage.text.toString()

            if(!messageIsCorrect(message)) return@setOnClickListener

            messagesRef.push().setValue(message)
            editTextMessage.setText("")
        }
    }

    private fun messageIsCorrect(message: String): Boolean {
        var correctResult = true
        if (message == "") {
            Toast.makeText(this, "Message input is empty!", Toast.LENGTH_SHORT).show()
            correctResult = false
        }

        if (message.length > MAX_MESSAGE_LENGTH) {
            Toast.makeText(this, "Message text cannot contains more than $MAX_MESSAGE_LENGTH symbols!", Toast.LENGTH_SHORT).show()
            correctResult = false
        }

        return correctResult
    }


}
