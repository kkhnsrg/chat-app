package edu.kokhan.simplechat.model

class UserCredentials {

    constructor()

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    var username: String = ""
    var password: String = ""
}