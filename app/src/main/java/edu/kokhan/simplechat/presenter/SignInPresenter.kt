package edu.kokhan.simplechat.presenter;

class SignInPresenter(private val view: View) {

    private val interactor: SignInInteractor = SignInInteractor(this)

    fun signInAction(username: String, pass: String) {
        var userExist = false
        val users = interactor.getUsers()

        //TODO get only one user
        for (user in users) {
            if (user.username == username && user.password == pass)
                userExist = true
        }

        if (username != "" && pass != "" && userExist) {
            view.correctSignIn(username)
        } else {
            view.incorrectSignIn()
            view.clearCredentialsFields()
        }
    }

    interface View {
        fun clearCredentialsFields()
        fun correctSignIn(username: String)
        fun incorrectSignIn()
    }
}
