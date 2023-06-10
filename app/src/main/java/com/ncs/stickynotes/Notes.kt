package com.ncs.stickynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val content:String,
    val backgroundDrawable: ByteArray

)