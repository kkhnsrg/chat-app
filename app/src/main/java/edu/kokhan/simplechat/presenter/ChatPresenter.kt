package edu.kokhan.simplechat.presenter

import android.widget.Toast
import edu.kokhan.simplechat.model.Message
import java.util.*

class ChatPresenter(private val mView: View) {

    val MAX_MESSAGE_LENGTH = 666

    private val interactor: ChatInteractor = ChatInteractor(this)

    fun sendNewMessage(message: String, username: String) {
        val chatMessage = Message(message, username)

        interactor.sendNewMessageToChat(chatMessage)
    }

    fun refreshCurrentChatList(currentChatMessage: ArrayList<Message>) {
        mView.refreshCurrentChatList(currentChatMessage)
    }

    fun messageIsCorrect(message: String): Boolean {
        var correctResult = true
        if (message == "") {
            mView.showEmptyInputMessage()
            correctResult = false
        }

        if (message.length > MAX_MESSAGE_LENGTH) {
            mView.showLargeInputMessage()
            correctResult = false
        }

        return correctResult
    }

    interface View {
        fun refreshCurrentChatList(currentChatMessage: ArrayList<Message>)
        fun showEmptyInputMessage()
        fun showLargeInputMessage()
    }
}