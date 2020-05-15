package com.github.sunyawhite.notereader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sunyawhite.notereader.Model.INoteRepository
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "edit_note_id"

/**
 * A simple [Fragment] subclass.
 * Use the [EditMoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditMoteFragment : Fragment() {
    private var id: Long? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_mote, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment EditMoteFragment.
         */
        @JvmStatic
        fun newInstance(id: Long) =
            EditMoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
    }
}
