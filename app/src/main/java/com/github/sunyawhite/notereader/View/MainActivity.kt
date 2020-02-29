package com.github.sunyawhite.notereader.View

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(),
    NoteFragment.OnListFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainDynamicFragment, NoteFragment.newInstance(this.getColumnCount()), NoteFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onListClick(id : Long) {
        if(supportFragmentManager.findFragmentByTag(DisplayNoteFragment.TAG) != null)
            supportFragmentManager.popBackStack()

        // Переход к другому фрагменту
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit)
            .replace(R.id.mainHelperFragment, DisplayNoteFragment.newInstance(id), DisplayNoteFragment.TAG)
            .addToBackStack(null)
            .commit()
    }


    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount){
            1 -> finish()
            else -> super.onBackPressed()
        }
    }

    private fun getColumnCount() : Int =
        when (resources.getBoolean(R.bool.isTablet) && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            true -> 2
            false -> 1
        }
}
