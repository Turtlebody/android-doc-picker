package com.greentoad.turtlebody.docpicker.sample.picker_result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.sample.R
import com.greentoad.turtlebody.docpicker.ui.components.ActivityLibMain
import kotlinx.android.synthetic.main.activity_result.*

class ActivityResults : AppCompatActivity() {

    private var mList: ArrayList<Uri> = arrayListOf()
    private var mAdapter: ResultAdapter = ResultAdapter()
    private var mPickerConfig: DocPickerConfig = DocPickerConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        toolbar.title = "Selected Doc"
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.tb_doc_picker_ic_arrow_back_black_24dp)

        if(intent.extras!=null){
            mList = intent.getSerializableExtra(ActivityLibMain.B_ARG_URI_LIST) as ArrayList<Uri>
            mPickerConfig = intent.getSerializableExtra(DocPickerConfig.ARG_BUNDLE) as DocPickerConfig
        }
        initAdapter()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAdapter() {
        mAdapter.setData(mList)
        mAdapter.setPickerConfig(mPickerConfig)
        activity_result_recycler_view.layoutManager = LinearLayoutManager(this)
        activity_result_recycler_view.adapter = mAdapter
    }


}
