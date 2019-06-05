package edu.kokhan.simplechat.presenter

import edu.kokhan.simplechat.model.Message
import java.util.*

class ChatPresenter(private val view: View) {

    val MAX_MESSAGE_LENGTH = 666

    private val interactor: ChatInteractor = ChatInteractor(this)

    fun sendNewMessage(message: String, username: String) {
        if(messageIsCorrect(message)) {
            val chatMessage = Message(message, username)
            interactor.sendNewMessageToChat(chatMessage)
            view.clearMessageInput()
        }
    }

    fun refreshCurrentChatList(currentChatMessage: ArrayList<Message>) {
        view.refreshCurrentChatList(currentChatMessage)
    }

    private fun messageIsCorrect(message: String): Boolean {
        var correctResult = true
        if (message == "") {
            view.showEmptyInputMessage()
            correctResult = false
        }
        if (message.length > MAX_MESSAGE_LENGTH) {
            view.showLargeInputMessage()
            correctResult = false
        }
        return correctResult
    }

    interface View {
        fun refreshCurrentChatList(currentChatMessage: ArrayList<Message>)
        fun showEmptyInputMessage()
        fun showLargeInputMessage()
        fun clearMessageInput()
    }
}