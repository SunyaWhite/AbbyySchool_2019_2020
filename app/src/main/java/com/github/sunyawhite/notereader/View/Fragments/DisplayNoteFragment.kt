package com.github.sunyawhite.notereader.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import kotlinx.android.synthetic.main.fragment_display_note.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.NullPointerException

private const val ARG_PARAM1 = "display_fragment_note_id"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayNoteFragment : Fragment() {

    private var noteId : Long? = null

    // Repository to deal with database
    private val repository : INoteRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId =  it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = runBlocking{
        val note = async { getNoteById(noteId ?: 0) }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_display_note, container, false)
        view.fullText.text = note.await().Text
        view.imageView.setImageResource(R.drawable.cat1)
        return@runBlocking view
    }


    private suspend fun getNoteById(id: Long) : Note  = withContext(Dispatchers.IO) {
        return@withContext repository.getNoteById(id) ?: throw IllegalArgumentException("id is null")
    }

    companion object {
        // Tag for the fragment
        const val TAG = "DISPLAY_NOTE"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param id Parameter 1.
         * @return A new instance of fragment DisplayNoteFragment.
         */
        @JvmStatic
        fun newInstance(id : Long) =
            DisplayNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
    }
}
