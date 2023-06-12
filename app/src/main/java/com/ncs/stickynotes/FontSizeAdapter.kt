package com.ncs.stickynotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FontSizeAdapter(
    private val fontSizeList: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<FontSizeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_font_size, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fontSize = fontSizeList[position]
        holder.bind(fontSize)

    }

    override fun getItemCount(): Int {
        return fontSizeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fontSizeTextView: TextView = itemView.findViewById(R.id.fontSizeTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val fontSize = fontSizeList[position]
                    onItemClick(fontSize)
                }
            }
        }

        fun bind(fontSize: Int) {
            fontSizeTextView.text = fontSize.toString()
        }
    }

}
