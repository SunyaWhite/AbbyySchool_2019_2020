package com.github.sunyawhite.notereader.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.Model.NoteRepository
import com.github.sunyawhite.notereader.R

//src/main/java/com/github/sunyawhite/notereader/Model/

class MainActivity : AppCompatActivity(), NoteFragment.OnListFragmentInteractionListener {

    // Repository to deal with database
    private var repository : INoteRepository = NoteRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainDynamicFragment, NoteFragment.newInstance(1), NoteFragment.TAG)
                .addToBackStack(NoteFragment.TAG)
                .commit()
    }

    override fun getListOfItems(): List<Note> =
        this.repository.getAllNotes() ?: emptyList<Note>()

    override fun onListClick(item: Note) {
        if(supportFragmentManager.findFragmentByTag(NoteFragment.TAG) != null)
            supportFragmentManager.popBackStack()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainDynamicFragment, DisplayNoteFragment.newInstance(item.Text, item.DrawableRes))
            .addToBackStack(DisplayNoteFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1){
            finish()
        }
        else {
            super.onBackPressed()
        }
    }
}
