import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.sunyawhite.notereader.R
import com.github.sunyawhite.notereader.Model.Note
import kotlinx.android.synthetic.main.fragment_note.view.*

/**
 * [RecyclerView.Adapter] that can display a [Note] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NoteRecyclerViewAdapter(
    private val mValues: List<Note>,
    private val mListener: NoteFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Note
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListClick(item.Id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTextView.text = item.Text
        holder.mLabelView.text = "${item.Date}"
        holder.mImageView.setImageResource(item.DrawableRes)
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.noteLabelView
        val mTextView: TextView = mView.noteTextView
        val mImageView : ImageView = mView.noteImageView

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}
