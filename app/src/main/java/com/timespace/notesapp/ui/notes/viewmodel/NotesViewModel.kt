package com.timespace.notesapp.ui.home.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.timespace.notesapp.database.prefrence.SharedPre
import com.timespace.notesapp.repositories.methods.MethodsRepo


class NotesViewModel @ViewModelInject constructor(
   val sharedPre: SharedPre,
    val methods: MethodsRepo,
   var databaseFirebase: FirebaseDatabase,
   application: Application
) : AndroidViewModel(Application()) {



}