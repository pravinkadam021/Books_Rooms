package com.example.books

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.books.adapter.BookAdapter
import com.example.books.database.AppDatabase
import com.example.books.database.BookEntity
import com.example.books.databinding.ActivityAddedBooksBinding

class AddedBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddedBooksBinding
    private lateinit var db: AppDatabase
    private var listItems = arrayOf("Book A", "Book B", "Book C")
    private var listBooks = arrayListOf<BookEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddedBooksBinding.inflate(layoutInflater)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "book_list.db").allowMainThreadQueries().build()

        listBooks = db.bookDao().getAllBooks() as ArrayList<BookEntity>

        if (listBooks.isEmpty()) {
            binding.rvBooks.visibility = GONE
            binding.tvNoData.visibility = VISIBLE
        } else {
            binding.rvBooks.visibility = VISIBLE
            binding.tvNoData.visibility = GONE
            binding.rvBooks.adapter = BookAdapter(this@AddedBooksActivity, listBooks)
        }

        setContentView(binding.root)
    }

    fun deleteBook(data: BookEntity, position: Int) {
        db.bookDao().deleteBook(data)
        listBooks.removeAt(position)
        binding.rvBooks.adapter?.notifyDataSetChanged()

        Toast.makeText(this, "Book Deleted Successfully", Toast.LENGTH_SHORT).show()

        if (listBooks.isEmpty()) {
            binding.rvBooks.visibility = GONE
            binding.tvNoData.visibility = VISIBLE
        } else {
            binding.rvBooks.visibility = VISIBLE
            binding.tvNoData.visibility = GONE
        }
    }

    fun updateBook(data: BookEntity, position: Int) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_layout_update_book, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        val etName = mDialogView.findViewById<EditText>(R.id.etName)
        val etNumber = mDialogView.findViewById<EditText>(R.id.etNumber)
        val etBook = mDialogView.findViewById<EditText>(R.id.etBook)

        etName.setText(data.name)
        etNumber.setText(data.number)
        etBook.setText(data.book)

        etBook.setOnClickListener {
            showChooseBookDialog(etBook)
        }

        mDialogView.findViewById<TextView>(R.id.tvSubmit).setOnClickListener {
            if (etName.text.length < 3) {
                Toast.makeText(this, "Please Enter Valid Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etNumber.text.length < 10) {
                Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (etBook.text.length < 2) {
                Toast.makeText(this, "Please Select Book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            data.name = etName.text.toString()
            data.number = etNumber.text.toString()
            data.book = etBook.text.toString()
            db.bookDao().updateBook(data)
            listBooks[position].name = data.name
            listBooks[position].number = data.number
            listBooks[position].book = data.book
            binding.rvBooks.adapter?.notifyItemChanged(position)
            Toast.makeText(this, "Book Updated Successfully", Toast.LENGTH_SHORT).show()
            mAlertDialog.dismiss()
        }
    }

    private fun showChooseBookDialog(etBook: EditText) {
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@AddedBooksActivity)
        mBuilder.setTitle("Choose Book")
        mBuilder.setSingleChoiceItems(
            listItems, -1
        ) { dialogInterface, i ->
            etBook.setText(listItems[i])
            dialogInterface.dismiss()
        }
        val mDialog: AlertDialog = mBuilder.create()
        mDialog.show()
    }
}