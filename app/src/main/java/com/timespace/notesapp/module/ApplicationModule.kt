package com.timespace.notesapp.module
/*
* © Copyright Ishant Sharma
* Android Developer
* JAVA/KOTLIN
* +91-7732993378
* ishant.sharma1947@gmail.com
* www.devishant.com
* */

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.timespace.notesapp.database.datastore.DataStoreBase
import com.timespace.notesapp.database.datastore.DataStoreCustom
import com.timespace.notesapp.database.prefrence.SharedPre
import com.timespace.notesapp.database.roomdatabase.AppDB
import com.timespace.notesapp.database.roomdatabase.DatabaseRepository
import com.timespace.notesapp.database.roomdatabase.MyDao
import com.timespace.notesapp.repositories.methods.MethodsRepo
import com.timespace.notesapp.repositories.dispatchers.DispatchersProviders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideCustomRepository(dataStore: DataStore<Preferences>): DataStoreBase =DataStoreCustom(
        dataStore
    )



    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =context.createDataStore(
        "TimeSpace"
    )
    @Provides
    fun provideDumbooDatabase(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(context, AppDB::class.java, "TimeSpaceDatabase")
            .fallbackToDestructiveMigration().build()
    }
    @Provides
    fun providePrefrence(@ApplicationContext context: Context):SharedPre= SharedPre.getInstance(
        context
    )!!

    @Provides
    fun providesPostDao(db: AppDB): MyDao = db.getDao()

    @Provides
    fun providesdatabaseRepository(@ApplicationContext context: Context, db: AppDB): DatabaseRepository = DatabaseRepository(db, context)

    @Provides
    fun provideMethodsRepo(@ApplicationContext context: Context, dataStore: DataStoreBase,sharedPre: SharedPre): MethodsRepo = MethodsRepo(context, dataStore,sharedPre)

    @Provides
    fun getString(): String = "Ishant Sharma"

    @Provides
    fun getFirebaseFirestore():FirebaseFirestore=FirebaseFirestore.getInstance()

    @Provides
    fun getFirebaseAuth():FirebaseAuth=FirebaseAuth.getInstance()

    @Provides
    fun getFirebaseRealTimeDatabase():FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun ProvideDispatchers():DispatchersProviders=object : DispatchersProviders{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

    @Module
    @InstallIn(ActivityComponent::class)
    class ActivityModule {
        @Provides
        fun fragmentManager(activity: Activity): FragmentManager {
            return (activity as AppCompatActivity).supportFragmentManager
        }


    }

}