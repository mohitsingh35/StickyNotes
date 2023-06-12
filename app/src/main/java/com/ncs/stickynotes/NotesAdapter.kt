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
    private var itemClickListener: NotesAdapter.OnItemClickListener? = null
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewContent: TextView = itemView.findViewById(R.id.content)
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = notes[position]
                    itemClickListener?.onItemClick(note)
                }
            }
        }

        fun bind(notes: Notes) {
            val backgroundByteArray: ByteArray = notes.backgroundDrawable
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(backgroundByteArray, 0, backgroundByteArray.size)
            val drawable: Drawable? = bitmap?.let { BitmapDrawable(it) }
            linearLayout.background = drawable
            textViewContent.text = notes.content

            if (notes.colorvalue != 0) {
                textViewContent.setTextColor(notes.colorvalue)
            } else {
                // Set a default text color if the note does not have a color value
                textViewContent.setTextColor(textViewContent.context.getColor(R.color.main_light))
                notes.colorvalue=0
            }
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
    fun setOnItemClickListener(listener: NotesAdapter.OnItemClickListener) {
        itemClickListener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(note: Notes)
    }

}