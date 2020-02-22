package com.github.sunyawhite.notereader.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import kotlinx.android.synthetic.main.fragment_display_note.view.*
import java.lang.NullPointerException

private const val ARG_PARAM1 = "display_fragment_note_id"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayNoteFragment : Fragment() {

    private var noteId : Long? = null
    private var listener: InteractWithDisplayNoteFragment? = null

    override fun onAttach(context: Context) {
        if (context is InteractWithDisplayNoteFragment) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId =  it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note = listener?.getNoteById(noteId ?: 0) ?: throw NullPointerException("Note can't be null")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_display_note, container, false)
        view.fullText.text = note.Text
        view.imageView.setImageResource(note.DrawableRes)
        return view
    }

    interface InteractWithDisplayNoteFragment{

        fun getNoteById(id : Long) : Note
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
