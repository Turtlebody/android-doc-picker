package com.greentoad.turtlebody.docpicker.ui.components.folder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.tb_doc_picker_fragment_doc_folder.*
import kotlinx.android.synthetic.main.tb_doc_picker_frame_progress.*
import org.jetbrains.anko.info

class DocFolderFragment : FragmentBase() {


    companion object {
        @JvmStatic
        fun newInstance(key: Int, b: Bundle?): Fragment {
            val bf: Bundle = b ?: Bundle()
            bf.putInt("fragment.key", key);
            val fragment = DocFolderFragment()
            fragment.arguments = bf
            return fragment
        }

        var sSelectedDocTypes: ArrayList<String> = arrayListOf()
    }

    private var mDocFolderAdapter: DocFolderAdapter = DocFolderAdapter()
    private var mDocFolderList: MutableLiveData<ArrayList<DocFolder>> = MutableLiveData()

    private var mPickerConfig: DocPickerConfig = DocPickerConfig()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tb_doc_picker_fragment_doc_folder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            mPickerConfig = arguments?.getSerializable(DocPickerConfig.ARG_BUNDLE) as DocPickerConfig
        }

        //live data : While loading if user back-press then the app will not crash
        mDocFolderList.observe(this, Observer {
            mDocFolderAdapter.setData(it)
            sSelectedDocTypes = mPickerConfig.mUserSelectedDocTypes
            SelectedDocsLayout(doc_folder_fragment_ll_for_selected_docs, sSelectedDocTypes,mPickerConfig).updateSelectedViews()
            frame_progress.visibility = View.GONE
        })

        initButton()
        initAdapter()
    }

    private fun initButton() {
        doc_folder_fragment_ll_filter.setOnClickListener {
            (activity as ActivityLibMain).startFragmentFilterFragment()
        }
    }

    fun onRefresh() {
        info { "content: $sSelectedDocTypes" }
        info { "content2: ${mPickerConfig.mUserSelectedDocTypes}" }
        if (!sSelectedDocTypes.containsAll(mPickerConfig.mUserSelectedDocTypes) || !mPickerConfig.mUserSelectedDocTypes.containsAll(
                sSelectedDocTypes
            )
        ) {
            fetchDocFolders()
        }
    }

    fun onFilterDone() {
        fetchDocFolders()
    }

    private fun initAdapter() {
        mDocFolderAdapter.setListener(object : DocFolderAdapter.OnDocFolderClickListener {
            override fun onFolderClick(pData: DocFolder) {
                info { "folderPath: ${pData.path}" }
                (activity as ActivityLibMain).startDocFragment(pData.path, mPickerConfig)
            }
        })

        doc_folder_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        doc_folder_fragment_recycler_view.adapter = mDocFolderAdapter
        fetchDocFolders()
    }


    private fun fetchDocFolders() {
        //info { "list2: ${args.toList()}" }
        val bucketFetch = Single.fromCallable<ArrayList<DocFolder>> {
            FileManager.fetchAudioFolderList(
                context!!,
                mPickerConfig.getUserSelectedExtArgs(mPickerConfig.mUserSelectedDocTypes)
            )
        }
        bucketFetch
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<DocFolder>> {
                override fun onSubscribe(@NonNull d: Disposable) {
                    frame_progress.visibility = View.VISIBLE
                }

                override fun onSuccess(@NonNull docFolders: ArrayList<DocFolder>) {
                    mDocFolderList.value = docFolders
                }

                override fun onError(@NonNull e: Throwable) {
                    frame_progress.visibility = View.GONE
                    e.printStackTrace()
                    info { "error: ${e.message}" }
                }
            })
    }


}
