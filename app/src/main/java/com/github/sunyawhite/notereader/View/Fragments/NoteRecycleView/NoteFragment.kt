package com.github.sunyawhite.notereader.View.Fragments.NoteRecycleView

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.Exception

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [NoteFragment.OnListFragmentInteractionListener] interface.
 */
class NoteFragment : Fragment(),
    NoteRecyclerViewAdapter.OnNoteAdapterListener {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var noteAdapter : NoteRecyclerViewAdapter

    // Repository to deal with database
    private val repository: INoteRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = runBlocking {
        // Получение кол-ва колонок для отображения
        val columnCount = async { getColumnCount() }
        // Получение заметки для отображения
        val notes = async { getListOfItems() }

        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        noteAdapter =
            NoteRecyclerViewAdapter(
                notes.await(),
                listener,
                this@NoteFragment
            )

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount.await() <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount.await())
                }
                adapter = noteAdapter
            }
        }
        return@runBlocking view
    }

    override fun onResume() {
        super.onResume()
        onUpdateElement()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    // Получение данных из репозитория
    private suspend fun getListOfItems(): List<Note> = withContext(Dispatchers.IO) {
        val notes = async { repository.getAllNotes() }
        return@withContext notes.await() ?: emptyList<Note>()
    }

    // Получение кол-ва отображаемых столбцов (разное для планшетов и телефонов)
    private suspend fun getColumnCount(): Int = withContext(Dispatchers.Default) {
        return@withContext when (resources.getBoolean(R.bool.isTablet) && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            true -> 2
            false -> 1
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListFragmentInteractionListener {

        fun onListClick(id: Long)

        fun onEditButtonClick(id : Long)

        fun onShareButtonClick(intent : Intent)
    }


    // Статические поля и фабрика для создания нового элемента
    companion object {
        // Tag for this fragment
        const val TAG = "LIST_NOTE"

        // Factory pattern for NoteFragment
        @JvmStatic
        fun newInstance() =
            NoteFragment()
    }

    override fun onDeleteButtonClick(id: Long) {
        val builder = AlertDialog.Builder(activity)
        with(builder){
            setTitle(getString(R.string.Title))
            setMessage(getString(R.string.Message))
            setPositiveButton(getString(R.string.OK)) { dialog, which -> run {
                Toast.makeText(activity, getString(R.string.Confirmed), Toast.LENGTH_SHORT).show()
                deleteNote(id)
            }}
            setNegativeButton(getString(R.string.NO)) { dialog, which ->
                Toast.makeText(activity,
                    getString(R.string.Canceled), Toast.LENGTH_SHORT).show()
            }
            builder.create().show()
        }
    }

    // Удаление заметки
    private fun deleteNote(id : Long) {
        runBlocking {
            repository.deleteNote(id)
            onUpdateElement()
        }
    }

    // Оповещаем адаптер, что нужно обновить список
    private fun onUpdateElement(): Boolean {
        return try {
            runBlocking {
                noteAdapter.updateNoteList(repository.getAllNotes() ?: emptyList<Note>())
                noteAdapter.notifyDataSetChanged()
            }
            true
        } catch (exc : Exception){
            Log.e("NoteFragment", exc.message)
            false;
        }
    }
}
