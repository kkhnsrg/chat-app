package edu.kokhan.simplechat.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import edu.kokhan.simplechat.R
import edu.kokhan.simplechat.presenter.SignInPresenter
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity(), SignInPresenter.View {

    private val presenter = SignInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInButton.setOnClickListener {
            val username = editTextLogin.text.toString()
            val password = editTextPass.text.toString()
            presenter.signInAction(username, password)
        }
    }

    override fun onResume() {
        super.onResume()
        clearCredentialsFields()
    }

    override fun clearCredentialsFields() {
        editTextLogin.setText("")
        editTextPass.setText("")
    }

    override fun correctSignIn(username: String) {
        val intent = Intent(baseContext, ChatActivity::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }

    override fun incorrectSignIn() {
        Toast.makeText(this, "Incorrect credentials!", Toast.LENGTH_SHORT).show()
    }

}
