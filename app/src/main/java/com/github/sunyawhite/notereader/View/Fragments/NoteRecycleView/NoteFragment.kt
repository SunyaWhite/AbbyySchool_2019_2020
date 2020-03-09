import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.Note
import com.github.sunyawhite.notereader.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [NoteFragment.OnListFragmentInteractionListener] interface.
 */
class NoteFragment : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    // Repository to deal with database
    private val repository: INoteRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = runBlocking {
        // Получение кол-ва колонок для отображения
        val columnCount = async { getColumnCount() }
        val notes = async { getListOfItems() }

        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount.await() <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount.await())
                }
                adapter = NoteRecyclerViewAdapter(
                    notes.await(),
                    listener
                )
            }
        }
        return@runBlocking view
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

    // Получение кол-ва отображаемых столбцов
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
    }

    companion object {
        // Tag for this fragment
        const val TAG = "LIST_NOTE"

        // Factory pattern for NoteFragment
        @JvmStatic
        fun newInstance() =
            NoteFragment()
    }
}
