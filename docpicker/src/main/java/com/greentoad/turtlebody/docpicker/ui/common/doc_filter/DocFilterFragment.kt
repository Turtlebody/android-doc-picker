package com.greentoad.turtlebody.docpicker.ui.common.doc_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.Constants
import kotlinx.android.synthetic.main.tb_doc_picker_bottom_sheet_doc_filter_fragment.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DocFilterFragment : BottomSheetDialogFragment(), AnkoLogger {


    companion object {
        @JvmStatic
        fun newInstance(key: Int, b: Bundle?): BottomSheetDialogFragment {
            var bf: Bundle = b ?: Bundle()
            bf.putInt("fragment.key", key);
            val fragment = DocFilterFragment()
            fragment.arguments = bf
            return fragment
        }


    }

    private var mDocFilterList: ArrayList<DocTypeModel> = arrayListOf()
    private var mAdapter: DocFilterAdapter = DocFilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        info { "lod" }
        return inflater.inflate(R.layout.tb_doc_picker_bottom_sheet_doc_filter_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDocFilterList.add(DocTypeModel("Pdf"))
        mDocFilterList.add(DocTypeModel("Word"))
        mDocFilterList.add(DocTypeModel("Text"))
        mDocFilterList.add(DocTypeModel("Ppt"))

        initAdapter()


    }

    private fun initAdapter() {
        tb_doc_picker_bottom_sheet_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        tb_doc_picker_bottom_sheet_fragment_recycler_view.adapter = mAdapter

        mAdapter.setData(mDocFilterList)
    }


    override fun getTheme(): Int {
        return R.style.App_Dialog_BottomSheet
    }


    fun setListener(pListener: PlaylistCreateFragmentListener?) {
        //mListener = pListener
    }


    interface PlaylistCreateFragmentListener {
        fun onFragmentCreateDone(docType: String)
    }
}