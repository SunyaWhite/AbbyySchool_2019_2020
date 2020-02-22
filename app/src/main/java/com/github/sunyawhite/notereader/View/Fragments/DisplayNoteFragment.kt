package com.github.sunyawhite.notereader.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sunyawhite.notereader.R
import kotlinx.android.synthetic.main.fragment_display_note.view.*

private const val ARG_PARAM1 = "display_fragment_full_text"
private const val ARG_PARAM2 = "display_fragment_image_id"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayNoteFragment : Fragment() {
    private var fullText: String? = "MEOW-MEOW_MEOW"
    private var imageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fullText = it.getString(ARG_PARAM1)
            imageId = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_display_note, container, false)
        view.fullText.text = this.fullText;
        view.imageView.setImageResource(this.imageId ?: R.drawable.cat1)
        return view
    }


    companion object {
        // Tag for the fragment
        const val TAG = "LIST_NOTE"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param text Parameter 1.
         * @param image Parameter 2.
         * @return A new instance of fragment DisplayNoteFragment.
         */
        @JvmStatic
        fun newInstance(text: String, image: Int) =
            DisplayNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, text)
                    putInt(ARG_PARAM2, image)
                }
            }
    }
}
