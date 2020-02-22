package com.github.sunyawhite.notereader.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import org.koin.android.ext.android.inject

//src/main/java/com/github/sunyawhite/notereader/Model/

class MainActivity : AppCompatActivity(),
    NoteFragment.OnListFragmentInteractionListener,
    DisplayNoteFragment.InteractWithDisplayNoteFragment{

    // Repository to deal with database
    private val repository : INoteRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainDynamicFragment, NoteFragment.newInstance(1), NoteFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun getListOfItems(): List<Note> =
        this.repository.getAllNotes() ?: emptyList<Note>()

    override fun onListClick(id : Long) {
        if(supportFragmentManager.findFragmentByTag(DisplayNoteFragment.TAG) != null)
            supportFragmentManager.popBackStack()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainDynamicFragment, DisplayNoteFragment.newInstance(id), DisplayNoteFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun getNoteById(id: Long) : Note  =
        repository.getNoteById(id) ?: throw IllegalArgumentException("id is null")


    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount){
            1 -> finish()
            else -> super.onBackPressed()
        }
    }
}
