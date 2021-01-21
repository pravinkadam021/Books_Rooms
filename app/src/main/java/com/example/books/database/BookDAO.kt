package com.example.books.database

import androidx.room.*

@Dao
interface BookDAO {
    @Query("SELECT * FROM book")
    fun getAllBooks(): List<BookEntity>

    @Insert
    fun insertBook(vararg book: BookEntity)

    @Delete
    fun deleteBook(book: BookEntity)

    @Update
    fun updateBook(vararg book: BookEntity)
}