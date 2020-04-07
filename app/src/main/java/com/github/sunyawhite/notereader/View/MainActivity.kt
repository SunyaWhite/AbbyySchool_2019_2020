package com.github.sunyawhite.notereader.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.sunyawhite.notereader.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),
    NoteFragment.OnListFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : FloatingActionButton = findViewById(R.id.floatingActionButton)
        button.setOnClickListener{ v: View? ->
            startActivity(Intent(this, CameraActivity::class.java))
        }

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainDynamicFragment, NoteFragment.newInstance(), NoteFragment.TAG)
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
}
