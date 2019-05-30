package edu.kokhan.simplechat

class Message{

    constructor() {
    }

    constructor(textMessage: String, author: String, timeMessage: Long) {
        this.textMessage = textMessage
        this.author = author
        this.timeMessage = timeMessage
    }

    var textMessage: String = ""
    var author: String = ""
    var timeMessage: Long = 0

}