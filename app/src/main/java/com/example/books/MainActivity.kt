package com.example.books

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.books.database.AppDatabase
import com.example.books.database.BookEntity
import com.example.books.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private var listItems = arrayOf("Book A", "Book B", "Book C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "book_list.db").build()

        binding.etBook.setOnClickListener {
            showChooseBookDialog()
        }
        binding.tvSubmit.setOnClickListener {
            if (binding.etName.text.length < 3) {
                Toast.makeText(this, "Please Enter Valid Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etNumber.text.length < 10) {
                Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etBook.text.length < 2) {
                Toast.makeText(this, "Please Select Book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addBook()
        }
        binding.tvAddedBooks.setOnClickListener {
            startActivity(Intent(this, AddedBooksActivity::class.java))
        }

        setContentView(binding.root)
    }

    private fun addBook() {
        GlobalScope.launch {
            db.bookDao().insertBook(BookEntity(0, binding.etName.text.toString(), binding.etNumber.text.toString(), binding.etBook.text.toString()))
        }
        Toast.makeText(this, "Book Added Successfully", Toast.LENGTH_SHORT).show()
        binding.etName.setText("")
        binding.etNumber.setText("")
        binding.etBook.setText("")
    }

    private fun showChooseBookDialog() {
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Book")
        mBuilder.setSingleChoiceItems(
            listItems, -1
        ) { dialogInterface, i ->
            binding.etBook.setText(listItems[i])
            dialogInterface.dismiss()
        }
        val mDialog: AlertDialog = mBuilder.create()
        mDialog.show()
    }
}