package com.timespace.notesapp.repositories.dispatchers
/*
* © Copyright Ishant Sharma
* Android Developer
* JAVA/KOTLIN
* +91-7732993378
* ishant.sharma1947@gmail.com
* www.Kamakhyitssolution.com
* */
import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProviders {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}