package com.example.books.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.books.AddedBooksActivity
import com.example.books.R
import com.example.books.database.BookEntity
import com.example.books.databinding.IconBookBinding

class BookAdapter(private val context: Context, private val booklist: ArrayList<BookEntity>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.icon_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = booklist[position]
        holder.binding.tvName.text = data.name
        holder.binding.tvNumber.text = data.number
        holder.binding.tvBook.text = data.book

        holder.binding.tvDelete.setOnClickListener{
            (context as AddedBooksActivity).deleteBook(data,position)
        }
        holder.binding.tvUpdate.setOnClickListener{
            (context as AddedBooksActivity).updateBook(data,position)
        }
    }

    override fun getItemCount() = booklist.size

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding = IconBookBinding.bind(view)
    }

}