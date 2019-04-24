package com.greentoad.turtlebody.docpicker.ui.components.folder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.core.FileManager
import com.greentoad.turtlebody.docpicker.ui.base.FragmentBase
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

        var mSelectedDocTypes:ArrayList<String> = arrayListOf()
    }

    private var mDocFolderAdapter: DocFolderAdapter = DocFolderAdapter()
    private var mDocFolderList: MutableList<DocFolder> = arrayListOf()
    private var mPickerConfig: DocPickerConfig = DocPickerConfig()
    private var mDocTypeArgs: Array<String?> = arrayOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tb_doc_picker_fragment_doc_folder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments!=null){
            mPickerConfig = arguments?.getSerializable(DocPickerConfig.ARG_BUNDLE) as DocPickerConfig
        }
        initButton()
        initAdapter()
    }

    private fun initButton() {
        tb_doc_picker_doc_filter.setOnClickListener {
            (activity as ActivityLibMain).startFragmentCreate()
        }
    }

    fun onRefresh(){
        info { "content: $mSelectedDocTypes" }
        info { "content2: ${mPickerConfig.mUserSelectedDocTypes}" }
        if(!mSelectedDocTypes.containsAll(mPickerConfig.mUserSelectedDocTypes) || !mPickerConfig.mUserSelectedDocTypes.containsAll(mSelectedDocTypes)){
            mDocTypeArgs = mPickerConfig.getCustomExtArgs(mPickerConfig.mUserSelectedDocTypes)
            fetchDocFolders(mDocTypeArgs)
        }
    }

    fun onFilterDone(list: ArrayList<String>) {
        info { "list: $list" }
        mDocTypeArgs = mPickerConfig.getCustomExtArgs(list)
        fetchDocFolders(mDocTypeArgs)
    }

    private fun initAdapter() {
        mDocFolderAdapter.setListener(object : DocFolderAdapter.OnDocFolderClickListener {
            override fun onFolderClick(pData: DocFolder) {
                info { "folderPath: ${pData.path}" }
                (activity as ActivityLibMain).startDocFragment(pData.path,mPickerConfig)
            }
        })

        tb_doc_picker_folder_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        tb_doc_picker_folder_fragment_recycler_view.adapter = mDocFolderAdapter

        mDocTypeArgs = mPickerConfig.mExtArgs
        fetchDocFolders(mDocTypeArgs)
    }


    private fun fetchDocFolders(args: Array<String?>) {
        info { "list2: ${args.toList()}" }
        val bucketFetch = Single.fromCallable<ArrayList<DocFolder>> {
            FileManager.fetchAudioFolderList(context!!,args)
        }
        bucketFetch
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<DocFolder>> {
                override fun onSubscribe(@NonNull d: Disposable) {
                    frame_progress.visibility = View.VISIBLE
                }
                override fun onSuccess(@NonNull audioFolders: ArrayList<DocFolder>) {
                    mDocFolderList = audioFolders
                    info { "folders: $audioFolders" }
                    mDocFolderAdapter.setData(mDocFolderList)
                    frame_progress.visibility = View.GONE

                    mSelectedDocTypes = mPickerConfig.mUserSelectedDocTypes
                }
                override fun onError(@NonNull e: Throwable) {
                    frame_progress.visibility = View.GONE
                    e.printStackTrace()
                    info { "error: ${e.message}" }
                }
            })
    }





}
