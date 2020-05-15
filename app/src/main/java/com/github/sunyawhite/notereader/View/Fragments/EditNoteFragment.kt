package com.github.sunyawhite.notereader.View

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_display_note.view.*
import kotlinx.android.synthetic.main.fragment_display_note.view.imageView
import kotlinx.android.synthetic.main.fragment_edit_mote.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "edit_note_id"

/**
 * A simple [Fragment] subclass.
 * Use the [EditMoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : Fragment() {
    private var id: Long? = null

    private var editableNote : Note? = null

    private var listener: OnSaveEditedNoteListener? = null

    private val repository : INoteRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return runBlocking{
            val note = async(Dispatchers.IO){
                repository.getNoteById(id!!)
            }
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_edit_mote, container, false)

            view.saveNote.setOnClickListener { v: View? ->
                run {
                    handleOnClickSaveButton()
                }
            }

            editableNote = note.await()
            view.editNoteText.setText(editableNote?.Text)
            //view.imageView.setImageResource(R.drawable.cat1)
            Picasso.with(activity!!.applicationContext)
                .load(editableNote?.DrawableRes)
                .fit()
                .centerInside()
                .into(view.imageView)

            return@runBlocking view
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSaveEditedNoteListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun handleOnClickSaveButton(){
        runBlocking{
            var result = async(Dispatchers.IO) {
                repository.updateNote(Note(editableNote!!.Id, editableNote!!.Date, view!!.editNoteText.text.toString(), editableNote!!.DrawableRes)) }
            if(!result.await())
                Toast.makeText(listener as Context, "Unable to save edited note", Toast.LENGTH_SHORT)
            listener?.onClickSaveButton()
        }

    }

    interface OnSaveEditedNoteListener{

        fun onClickSaveButton()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment EditMoteFragment.
         */

        const val TAG = "EDIT_NOTE"

        @JvmStatic
        fun newInstance(id: Long) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
    }
}
