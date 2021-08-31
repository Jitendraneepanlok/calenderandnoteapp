package com.timespace.notesapp.database.roomdatabase

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MyDao {
    @Insert
    fun insert(example: EntityExample)
}