package com.github.sunyawhite.notereader.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_note.view.*
import kotlinx.android.synthetic.main.fragment_display_note.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class EditNoteActivity : AppCompatActivity() {

    private val repository : INoteRepository by inject()

    private var editableNote : Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        runBlocking {

            val note = async(Dispatchers.IO) {
                val id = intent.getLongExtra(TAG, -1)
                if(id == -1L)
                    null
                repository.getNoteById(id)
            }

            val text = findViewById<EditText>(R.id.editNoteText)
            val image = findViewById<ImageView>(R.id.imageView)
            val button = findViewById<Button>(R.id.saveNote)

            if(note.await() == null)
                finish()
            editableNote = note.await()

            text.setText(editableNote?.Text)
            Picasso.with(this@EditNoteActivity)
                .load(editableNote?.DrawableRes)
                .fit()
                .centerInside()
                .into(image)
            button.setOnClickListener { v: View? -> run {
                handleOnClickSaveButton(text.text.toString())
            }}
        }
    }

    private fun handleOnClickSaveButton(newText : String) {
        runBlocking{
            val result = async(Dispatchers.IO) {
                repository.updateNote(
                    Note(
                        editableNote!!.Id,
                        editableNote!!.Date,
                        newText,
                        editableNote!!.DrawableRes
                    )
                )
            }
            if(!result.await()) {
                Log.e("EditNoteActivity", "Unable to save edited note")
            }
            finish()
        }
    }

    companion object {
        const val TAG = "EDIT_NOTE"
    }
}
