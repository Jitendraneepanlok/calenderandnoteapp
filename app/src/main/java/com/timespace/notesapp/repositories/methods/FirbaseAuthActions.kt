package com.timespace.notesapp.repositories.methods

interface FirbaseAuthActions {

    fun VerificationCodeSent()
    fun startActivity(uid: String, phoneNumber: String?)
    fun Error(message : String )
    fun TimeOut()
}