package edu.kokhan.simplechat.model

import java.util.*

class Message {

    constructor()

    constructor(textMessage: String, author: String) {
        this.textMessage = textMessage
        this.author = author
        this.timeMessage = Date().time
    }

    var textMessage: String = ""
    var author: String = ""
    var timeMessage: Long = 0

}