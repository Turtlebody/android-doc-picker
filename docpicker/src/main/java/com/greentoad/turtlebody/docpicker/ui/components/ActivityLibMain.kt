package com.greentoad.turtlebody.docpicker.ui.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.DocConstants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.ui.base.ActivityBase
import com.greentoad.turtlebody.docpicker.ui.common.bottom_sheet_filter.DocFilterFragment
import com.greentoad.turtlebody.docpicker.ui.components.file.DocFragment
import com.greentoad.turtlebody.docpicker.ui.components.folder.DocFolderFragment
import kotlinx.android.synthetic.main.tb_doc_picker_activity_lib_main.*
import org.jetbrains.anko.info
import java.io.Serializable


class ActivityLibMain : ActivityBase(){

    companion object{
        const val B_ARG_URI_LIST = "activity.lib.main.uri.list"
    }


    private lateinit var mPickerConfig: DocPickerConfig
    private var mDocFilterFragment: BottomSheetDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tb_doc_picker_activity_lib_main)

        initToolbar(R.drawable.tb_doc_picker_ic_arrow_back_black_24dp,activity_lib_main_toolbar)
        toolbarTitle = "Select Folder"

        if (intent.extras != null) {
            mPickerConfig = intent.getSerializableExtra(DocPickerConfig.ARG_BUNDLE) as DocPickerConfig
        }

        startDocFolderFragment()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_open -> {
                //createPickFromDocumentsIntent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_content)
        when (fragment) {
            is DocFolderFragment -> finish()
            is DocFragment -> {
                super.onBackPressed()
                toolbarTitle = "Select Folder"
                activity_lib_main_toolbar_txt_count.visibility = View.GONE
                updateCounter(0)

                val fragment2 = supportFragmentManager.findFragmentById(R.id.frame_content)
                if(fragment2 is DocFolderFragment){
                    fragment2.onRefresh()
                }

            }
            else -> super.onBackPressed()
        }
    }


    /**
     * @param counter counter for selecting multiple media files
     */
    fun updateCounter(counter: Int) {
        activity_lib_main_toolbar_txt_count.text = "$counter"
    }

    fun sendBackData(list: ArrayList<Uri>) {
        if (list.isNotEmpty()) {
            val intent = Intent()
            intent.putExtra(B_ARG_URI_LIST, list as Serializable)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }


    private fun startDocFolderFragment() {
        toolbarTitle = "Select Folder"
        activity_lib_main_toolbar_txt_count.visibility = View.GONE

        val bundle = Bundle()
        bundle.putSerializable(DocPickerConfig.ARG_BUNDLE, mPickerConfig)

        val fragment = DocFolderFragment.newInstance(DocConstants.Fragment.DOC_FOLDER, bundle)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frame_content, fragment, DocFolderFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }


    fun startDocFragment(folderPath: String,pickerConfig: DocPickerConfig) {
        toolbarTitle = "Choose Doc"
        activity_lib_main_toolbar_txt_count.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putSerializable(DocPickerConfig.ARG_BUNDLE, pickerConfig)
        bundle.putString(DocFragment.B_ARG_FOLDER_PATH, folderPath)

        val fragment = DocFragment.newInstance(DocConstants.Fragment.DOC_LIST, bundle)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frame_content, fragment, DocFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }


    fun startFragmentFilterFragment() {
        val bundle = Bundle()
        bundle.putSerializable(DocPickerConfig.ARG_BUNDLE, mPickerConfig)

        info { "fragment: create" }
        mDocFilterFragment = DocFilterFragment.newInstance(DocConstants.Fragment.DOC_FILTER, bundle)
        (mDocFilterFragment as DocFilterFragment).setListener(object : DocFilterFragment.OnFilterDoneListener{
            override fun onFilterDone() {
                val fragment = supportFragmentManager.findFragmentById(R.id.frame_content)
                when (fragment) {
                    is DocFolderFragment -> {
                        info { "folder fragment" }
                        fragment.onFilterDone()
                    }
                    is DocFragment -> {
                        info { "doc fragment" }
                        fragment.onFilterDone()
                    }
                }
                mDocFilterFragment?.dismiss()
            }
        })
        mDocFilterFragment?.show(supportFragmentManager, DocFilterFragment::class.java.simpleName)
    }

}
