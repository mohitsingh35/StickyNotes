package com.ncs.stickynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncs.stickynotes.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var noteViewModel: NoteViewModel
    lateinit var binding: FragmentMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var buttonAddNote: Button
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        recyclerView = binding.recycler
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        val adapter = NotesAdapter(emptyList())
        adapter.setOnItemClickListener(object : NotesAdapter.OnItemClickListener {
            override fun onItemClick(notes: Notes) {
                val fragment = AddNoteFragment()
                val bundle = Bundle()
                bundle.putParcelable("selectedNote", notes)
                fragment.arguments = bundle
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.FragmentContainer, fragment)
                fragmentTransaction.commit()
            }
        })
        recyclerView.adapter = adapter
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(viewLifecycleOwner, { notes ->
            adapter.notes = notes
            adapter.notifyDataSetChanged()
            if(notes.isEmpty()){
                recyclerView.visibility=View.INVISIBLE
                binding.emptyDatabase.visibility=View.VISIBLE
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAddNote=binding.addNote
        buttonAddNote.setOnClickListener {
            showAddNoteFragment()
        }
    }

    private fun showAddNoteFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentContainer, AddNoteFragment())
        fragmentTransaction.commit()
    }

}