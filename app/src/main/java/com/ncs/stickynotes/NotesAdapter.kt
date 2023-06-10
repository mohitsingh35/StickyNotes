package com.ncs.stickynotes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(var notes: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewContent: TextView = itemView.findViewById(R.id.content)
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)


        fun bind(notes: Notes) {
            textViewContent.text = notes.content
            val backgroundByteArray: ByteArray =notes.backgroundDrawable
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(backgroundByteArray, 0, backgroundByteArray.size)
            val drawable: Drawable? = bitmap?.let { BitmapDrawable(it) }
            linearLayout.background=drawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.noteslayout, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}