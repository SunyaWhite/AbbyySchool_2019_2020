import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.R
import com.github.sunyawhite.notereader.Model.Note
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_note.view.*

/**
 * [RecyclerView.Adapter] that can display a [Note] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NoteRecyclerViewAdapter(
    private var mValues: List<Note>,
    private val mListener: NoteFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>(){

    // mListener == Context == Activity

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Note
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListClick(item.Id)
        }
    }

    fun updateNoteList(notes : List<Note>) {
        this.mValues = notes
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
        // Downloading image into ImageView
        Picasso.with((mListener as Context))
            .load(item.DrawableRes)
            .fit()
            .centerInside()
            .into(holder.mView.noteImageView)
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
        holder.mMenuButton.setOnClickListener { v: View? ->
            require(v != null)
            this.handlePopUpMenu(v, item.Id)
        }
    }

    @SuppressLint("ResourceType")
    private fun handlePopUpMenu(v : View?, id : Long){
        val menu = PopupMenu(mListener as Context, v)
        menu.setOnMenuItemClickListener { menuItem: MenuItem? ->  handleOnClickMenuItem(menuItem, id)}
        val inflater = menu.menuInflater
        inflater.inflate(R.xml.add_menu, menu.menu)
        menu.show()
    }

    private fun handleOnClickMenuItem(item : MenuItem?, id : Long) =
        when(item?.itemId){
            R.id.menuShare -> handleShareClick(id)
            R.id.menuDelete -> handleDeleteClick(id)
            R.id.menuEdit -> handleEditClick(id)
            else -> false
        }

    private fun handleShareClick(id : Long) : Boolean{
        require(mListener != null)
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, mValues.first{note -> note.Id == id}.Text)
        shareIntent.type = (mListener as Context).getString( R.string.text_plain)
        mListener.onShareButtonClick(shareIntent)
        return true
    }


    private fun handleDeleteClick(id : Long) : Boolean{
        require(mListener != null)
        mListener.onDeleteButtonClick(id)
        return true
    }

    private fun handleEditClick(id : Long) : Boolean{
        require(mListener != null)
        mListener.onEditButtonClick(id)
        return true
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.noteLabelView
        val mTextView: TextView = mView.noteTextView
        val mImageView : ImageView = mView.noteImageView
        val mMenuButton : ImageButton = mView.menuButton

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}

