package com.gdi.conferenceplanner.util.handlers

object ValidationHandler {
    fun isInputLengthValid(input: String): Boolean {
        return input.isNotEmpty()
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}\$")
        return emailRegex.matches(email)
    }
    fun isUsernameValid(username: String): Boolean {
        val usernameRegex = Regex("^[a-zA-Z0-9_.-]{4,20}\$")
        return usernameRegex.matches(username)
    }
    fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()-+])[A-Za-z\\d!@#\$%^&*()-+]{8,20}$")
        return pattern.matches(password)
    }

}