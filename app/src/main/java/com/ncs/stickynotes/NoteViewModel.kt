package com.ncs.stickynotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao: NotesDAO
    private val allNotes: LiveData<List<Notes>>

    init {
        val database = NotesDatabase.getDatabase(application)
        noteDao = database.notesDao()
        allNotes = noteDao.getAllNotes()
    }

    fun getAllNotes(): LiveData<List<Notes>> {
        return allNotes
    }

    fun insert(notes: Notes) = viewModelScope.launch {
        noteDao.insert(notes)
    }

    fun delete(notes: Notes) = viewModelScope.launch {
        noteDao.delete(notes)
    }
}