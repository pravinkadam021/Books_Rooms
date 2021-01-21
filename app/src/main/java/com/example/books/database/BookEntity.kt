package com.example.books.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "book") var book: String
)