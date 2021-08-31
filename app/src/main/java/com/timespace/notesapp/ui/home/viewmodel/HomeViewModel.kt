package com.timespace.notesapp.ui.home.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.timespace.notesapp.database.datastore.DataStoreBase
import com.timespace.notesapp.database.prefrence.SharedPre
import com.timespace.notesapp.repositories.methods.FirbaseAuthActions
import com.timespace.notesapp.repositories.methods.MethodsRepo

class HomeViewModel @ViewModelInject constructor(
   val sharedPre: SharedPre,
    val methods: MethodsRepo,
   application: Application
) : AndroidViewModel(Application()) {



}