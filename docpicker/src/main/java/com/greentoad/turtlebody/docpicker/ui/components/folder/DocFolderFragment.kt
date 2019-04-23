package com.greentoad.turtlebody.docpicker.ui.components.folder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.Constants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.core.FileManager
import com.greentoad.turtlebody.docpicker.ui.base.FragmentBase
import com.greentoad.turtlebody.docpicker.ui.common.doc_filter.DocFilterFragment
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
    }

    private var mDocFolderAdapter: DocFolderAdapter = DocFolderAdapter()
    private var mDocFolderList: MutableList<DocFolder> = arrayListOf()
    private var mPickerConfig: DocPickerConfig = DocPickerConfig()

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
            startFragmentCreate()
        }
    }

    private fun initAdapter() {
        mDocFolderAdapter.setListener(object : DocFolderAdapter.OnDocFolderClickListener {
            override fun onFolderClick(pData: DocFolder) {
                info { "folderPath: ${pData.path}" }
                (activity as ActivityLibMain).startDocFragment(pData.path)
            }
        })

        tb_doc_picker_folder_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
        tb_doc_picker_folder_fragment_recycler_view.adapter = mDocFolderAdapter
        fetchDocFolders()
    }


    private fun fetchDocFolders() {
        val bucketFetch = Single.fromCallable<ArrayList<DocFolder>> {
            FileManager.fetchAudioFolderList(context!!,mPickerConfig.mExtArgs)
        }
        bucketFetch
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<DocFolder>> {
                override fun onSubscribe(@NonNull d: Disposable) {
                    progress_view.visibility = View.VISIBLE
                }
                override fun onSuccess(@NonNull audioFolders: ArrayList<DocFolder>) {
                    mDocFolderList = audioFolders
                    info { "folders: $audioFolders" }
                    mDocFolderAdapter.setData(mDocFolderList)
                    progress_view.visibility = View.GONE
                }
                override fun onError(@NonNull e: Throwable) {
                    progress_view.visibility = View.GONE
                    e.printStackTrace()
                    info { "error: ${e.message}" }
                }
            })
    }


    private fun startFragmentCreate() {
        info { "fragment: create" }
        val mPlaylistCreateFragment = DocFilterFragment.newInstance(Constants.Fragment.DOC_FILTER, Bundle())
        //(mPlaylistCreateFragment as DocFilterFragment).setListener(this)
        activity?.supportFragmentManager?.let {
            mPlaylistCreateFragment.show(it, DocFilterFragment::class.java.simpleName)
        }
    }


}
