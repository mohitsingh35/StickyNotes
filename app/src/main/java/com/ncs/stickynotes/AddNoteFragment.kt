package com.ncs.stickynotes

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ncs.stickynotes.databinding.FragmentAddNoteBinding
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog
import vadiole.colorpicker.ColorPickerView.Companion.defaultColor
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
    private lateinit var layoutid: Drawable
    private var receivedNote: Notes? = null
    lateinit var colorButton: Button
    lateinit var fontsize:Button
    var colorvalue:Int = 0
    var size:Float= 0.0F


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        colorButton=binding.color
        colorButton.setOnClickListener {
          textcolor()
        }
        fontsize=binding.fontsize
        fontsize.setOnClickListener {
            showFontSizeBottomSheet()
        }
        arguments?.let {
            receivedNote = it.getParcelable(ARG_NOTE)
        }
        if (receivedNote == null) {
            binding.editTextContent.textSize=size
        } else {

            val bitmap = receivedNote?.backgroundDrawable?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            val drawable = bitmap?.let { BitmapDrawable(resources, it) }
            binding.layout.background = drawable
            binding.editTextContent.setText(receivedNote?.content)
            binding.editTextContent.doAfterTextChanged {
                receivedNote?.content = it.toString()
            }
            receivedNote?.colorvalue?.let { binding.editTextContent.setTextColor(it) }
            binding.Save.visibility=View.INVISIBLE
            binding.update.visibility=View.VISIBLE
            binding.delete.visibility=View.VISIBLE
            binding.delete.setOnClickListener {
                deleteNote()
            }
            binding.update.setOnClickListener {
                SaveNote()
            }

        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton = binding.back
        saveNote = binding.Save
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
        constraintLayout = binding.layout
        recyclerView = binding.recycler
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val imageData = listOf(
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.sixth,
            R.drawable.seventh,
            R.drawable.eighth,
            R.drawable.ninth,
            R.drawable.tenth,
            R.drawable.eleventh,
            R.drawable.twelth
        )
        val adapter = MyAdapter(imageData)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    private fun SaveNote() {
        editTextContent = binding.editTextContent
        val backgroundDrawable = constraintLayout.background
        val byteArrayOutputStream = ByteArrayOutputStream()
        backgroundDrawable?.let {
            val bitmap = (it as BitmapDrawable).bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        }
        val backgroundByteArray = byteArrayOutputStream.toByteArray()
        val content = editTextContent.text.toString()
        val textcolour=editTextContent.currentTextColor

        if (receivedNote != null) {
            receivedNote?.content = content
            receivedNote?.backgroundDrawable = backgroundByteArray
            receivedNote?.colorvalue=textcolour
            receivedNote?.size=size
            if (colorvalue == 0) {
                receivedNote?.colorvalue = editTextContent.currentTextColor
            }
            noteViewModel.update(receivedNote!!)

            Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()

        } else {
            // Insert a new note
            val note = Notes(0, content = content, backgroundDrawable = backgroundByteArray, colorvalue = editTextContent.currentTextColor,
                size=editTextContent.textSize)
            noteViewModel.insert(note)
            Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onItemClick(position: Int) {
        when (position) {
            0 -> constraintLayout.setBackgroundResource(R.drawable.first)
            1 -> constraintLayout.setBackgroundResource(R.drawable.second)
            2 -> constraintLayout.setBackgroundResource(R.drawable.third)
            3 -> constraintLayout.setBackgroundResource(R.drawable.fourth)
            4 -> constraintLayout.setBackgroundResource(R.drawable.fifth)
            5 -> constraintLayout.setBackgroundResource(R.drawable.sixth)
            6 -> constraintLayout.setBackgroundResource(R.drawable.seventh)
            7 -> constraintLayout.setBackgroundResource(R.drawable.eighth)
            8 -> constraintLayout.setBackgroundResource(R.drawable.ninth)
            9 -> constraintLayout.setBackgroundResource(R.drawable.tenth)
            10 -> constraintLayout.setBackgroundResource(R.drawable.eleventh)
            11 -> constraintLayout.setBackgroundResource(R.drawable.twelth)
        }

    }

    companion object {
        private const val ARG_NOTE = "selectedNote"
        fun newInstance(note: Notes? = null): AddNoteFragment {
            val fragment = AddNoteFragment()
            val args = Bundle().apply {
                putParcelable(ARG_NOTE, note)
            }
            fragment.arguments = args
            return fragment
        }

    }
    private fun deleteNote() {
        receivedNote?.let { note ->
            noteViewModel.delete(note)
            Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.FragmentContainer, MainFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }
    fun textcolor(){
        val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
            .setColorModel(ColorModel.RGB)
            .onColorSelected { color: Int ->
                binding.editTextContent.setTextColor(color)
                colorvalue=color
            }
            .setColorModelSwitchEnabled(true)
            .setButtonOkText(android.R.string.ok)
            .setButtonCancelText(android.R.string.cancel)
            .create()
        colorPicker.show(childFragmentManager, "color_picker")
    }
    fun showFontSizeBottomSheet() {
        val fontSizeList = listOf(12, 16, 20, 24, 28,32,36,40,44,48,52,56,60,64,68)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = GridLayoutManager(requireContext(),5)
        recyclerView.adapter = FontSizeAdapter(fontSizeList) { fontSize ->
            binding.editTextContent.textSize = fontSize.toFloat()
            size= fontSize.toFloat()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(recyclerView)
        bottomSheetDialog.show()
    }

    }

