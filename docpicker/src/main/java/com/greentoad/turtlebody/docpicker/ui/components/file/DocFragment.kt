package com.greentoad.turtlebody.docpicker.ui.components.file


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.core.FileManager
import com.greentoad.turtlebody.docpicker.ui.base.FragmentBase
import com.greentoad.turtlebody.docpicker.ui.common.bottom_sheet_filter.SelectedDocsLayout
import com.greentoad.turtlebody.docpicker.ui.components.ActivityLibMain
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.tb_doc_picker_fragment_doc.*
import kotlinx.android.synthetic.main.tb_doc_picker_frame_progress.*
import org.jetbrains.anko.info
import java.io.File

class DocFragment : FragmentBase(), DocAdapter.OnDocClickListener {

    companion object {

        @JvmStatic
        fun newInstance(key: Int, b: Bundle?): Fragment {
            val bf: Bundle = b ?: Bundle()
            bf.putInt("fragment.key", key);
            val fragment = DocFragment()
            fragment.arguments = bf
            return fragment
        }

        const val B_ARG_FOLDER_PATH = "args.folder.path"
    }

    private var mFolderPath: String = ""

    private var mDocAdapter: DocAdapter = DocAdapter()
    private var mDocModelList: ArrayList<DocModel> = arrayListOf()
    var mPickerConfig: DocPickerConfig = DocPickerConfig()
    var mUriList: ArrayList<Uri> = arrayListOf()

    private var mLiveData: MutableLiveData<ArrayList<DocModel>> = MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tb_doc_picker_fragment_doc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            mFolderPath= it.getString(B_ARG_FOLDER_PATH,"")
            mPickerConfig = it.getSerializable(DocPickerConfig.ARG_BUNDLE) as DocPickerConfig
            info { "folderPath: $mFolderPath" }
        }

        ////live data : While loading recyclerView if user back-press then the app will not crash
        mLiveData.observe(this, Observer {
            mDocAdapter.setData(mDocModelList)
            SelectedDocsLayout(doc_fragment_ll_for_selected_docs, mPickerConfig.mUserSelectedDocTypes,mPickerConfig)
                .updateSelectedViews()
            frame_progress.visibility = View.GONE
        })

        initButton()
        initAdapter()
    }

    private fun initButton() {
        if (!mPickerConfig.mAllowMultiSelection) {
            doc_fragment_btn_done.visibility = View.GONE
        }
        doc_fragment_btn_done.setOnClickListener {
            getAllUris()
        }

        doc_fragment_filter.setOnClickListener {
            (activity as ActivityLibMain).startFragmentFilterFragment()
        }

        doc_fragment_ll_for_selected_docs.setOnClickListener {
            (activity as ActivityLibMain).startFragmentFilterFragment()
        }
    }

    private fun getAllUris() {
        for(i in mDocModelList){
            if(i.isSelected){
                mUriList.add(FileManager.getContentUri(context!!, File(i.filePath)))
            }
        }

        if(mUriList.isNotEmpty()){
            (activity as ActivityLibMain).sendBackData(mUriList)
        }
    }


    private fun initAdapter() {
        mDocAdapter.setListener(this)
        mDocAdapter.setPickerConfig(mPickerConfig)

        doc_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        doc_fragment_recycler_view.adapter = mDocAdapter
        fetchDocFolders()
    }

    override fun onDocCheck(pData: DocModel) {
        if(!mPickerConfig.mAllowMultiSelection){
            if(mPickerConfig.mShowConfirmationDialog){
                val simpleAlert = AlertDialog.Builder(context!!)
                simpleAlert.setMessage("Are you sure to select ${pData.name}")
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        (activity as ActivityLibMain).sendBackData(arrayListOf(FileManager.getContentUri(context!!, File(pData.filePath))))
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()  }
                simpleAlert.show()
            }
            else{
                (activity as ActivityLibMain).sendBackData(arrayListOf(FileManager.getContentUri(context!!, File(pData.filePath))))
            }
        }
        else{
            val selectedIndex = mDocModelList.indexOf(pData)
            if(selectedIndex >= 0){
                //toggle
                mDocModelList[selectedIndex].isSelected = !(mDocModelList[selectedIndex].isSelected)
                //update ui
                mDocAdapter.updateIsSelected(mDocModelList[selectedIndex])
            }

            var size = 0
            for(i in mDocModelList){
                if(i.isSelected){
                    size += 1
                }
            }
            (activity as ActivityLibMain).updateCounter(size)
            doc_fragment_btn_done.isEnabled = size>0
        }
    }

    fun onFilterDone() {
        (activity as ActivityLibMain).updateCounter(0)
        doc_fragment_btn_done.isEnabled = false
        fetchDocFolders()
    }

    @SuppressLint("CheckResult")
    private fun fetchDocFolders() {
        val fileItems = Single.fromCallable<Boolean> {
            mDocModelList.clear()
            //mDocModelList = FileManager.getDocFilesInFolder(context!!,mFolderPath)
            val tempArray = FileManager.getDocFilesInFolder(context!!,mFolderPath)
            info { "files size: ${tempArray.size}" }
            for(i in tempArray){
                for (j in mPickerConfig.getUserSelectedExtArgs(mPickerConfig.mUserSelectedDocTypes)) {
                    if (File(i.filePath).extension == (j!!.substring(2))) {
//                        if(i.size>0)
                            mDocModelList.add(i)
                    }
                }
            }
            info { "list size: ${mDocModelList.size}" }
            info { "added all: ${mDocModelList}" }
            true
        }

        fileItems.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(@NonNull d: Disposable) {
                    frame_progress.visibility = View.VISIBLE
                }
                override fun onSuccess(t: Boolean) {
                    mLiveData.value = mDocModelList
                }
                override fun onError(@NonNull e: Throwable) {
                    frame_progress.visibility = View.GONE
                    info { "error: ${e.message}" }
                }
            })
    }

}
