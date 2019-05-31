package edu.kokhan.simplechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.content.Intent
import android.widget.Toast


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInButton.setOnClickListener{
            val currentLoginValue = editTextLogin.text.toString()
            if(currentLoginValue != "" && currentLoginValue.length > 3){
                val intent = Intent(baseContext, ChatActivity::class.java)
                intent.putExtra("USERNAME", currentLoginValue)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect username", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
