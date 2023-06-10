package com.ncs.stickynotes

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.Telephony.Mms.Part.TEXT
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncs.stickynotes.databinding.FragmentAddNoteBinding
import java.io.ByteArrayOutputStream
import kotlin.properties.Delegates


class AddNoteFragment : Fragment() ,MyAdapter.OnItemClickListener {
    lateinit var noteViewModel: NoteViewModel
    lateinit var editTextContent: EditText
    lateinit var binding: FragmentAddNoteBinding
    lateinit var backButton: Button
    lateinit var saveNote: Button
    lateinit var recyclerView: RecyclerView
    lateinit var constraintLayout: ConstraintLayout
    private lateinit var layoutid :Drawable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentAddNoteBinding.inflate(inflater,container,false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton=binding.back
        saveNote=binding.Save
        saveNote.setOnClickListener {
            SaveNote()
        }
        backButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.FragmentContainer, MainFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        constraintLayout=binding.layout
        recyclerView=binding.recycler
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val imageData = listOf(R.drawable.first, R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth,R.drawable.sixth,
            R.drawable.seventh,R.drawable.eighth,R.drawable.ninth,R.drawable.tenth,R.drawable.eleventh,R.drawable.twelth)
        val adapter = MyAdapter(imageData)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    private fun SaveNote() {
        editTextContent=binding.editTextContent
        val backgroundDrawable = constraintLayout.background
        val byteArrayOutputStream = ByteArrayOutputStream()
        backgroundDrawable?.let {
            val bitmap = (it as BitmapDrawable).bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        }
        val backgroundByteArray = byteArrayOutputStream.toByteArray()
        val content = editTextContent.text.toString()
        val notes = Notes(0, content = content,backgroundDrawable = backgroundByteArray)
        noteViewModel.insert(notes)

    }
    override fun onItemClick(position: Int) {
        when(position){
            0->constraintLayout.setBackgroundResource(R.drawable.first)
            1->constraintLayout.setBackgroundResource(R.drawable.second)
            2->constraintLayout.setBackgroundResource(R.drawable.third)
            3->constraintLayout.setBackgroundResource(R.drawable.fourth)
            4->constraintLayout.setBackgroundResource(R.drawable.fifth)
            5->constraintLayout.setBackgroundResource(R.drawable.sixth)
            6->constraintLayout.setBackgroundResource(R.drawable.seventh)
            7->constraintLayout.setBackgroundResource(R.drawable.eighth)
            8->constraintLayout.setBackgroundResource(R.drawable.ninth)
            9->constraintLayout.setBackgroundResource(R.drawable.tenth)
            10->constraintLayout.setBackgroundResource(R.drawable.eleventh)
            11->constraintLayout.setBackgroundResource(R.drawable.twelth)
        }

    }

}