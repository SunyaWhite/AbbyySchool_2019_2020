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
    private val repository : INoteRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Получение кол-ва колонок для отображения
        val columnCount = getColumnCount()

        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = NoteRecyclerViewAdapter(
                    getListOfItems(),
                    listener
                )
            }
        }
        return view
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
    private fun getListOfItems(): List<Note> =
        this.repository.getAllNotes() ?: emptyList<Note>()

    // Получение кол-ва отображаемых столбцов
    private fun getColumnCount() : Int =
        when (resources.getBoolean(R.bool.isTablet) && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            true -> 2
            false -> 1
        }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListFragmentInteractionListener {

        fun onListClick(id : Long)
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
