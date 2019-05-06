package com.greentoad.turtlebody.docpicker.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.sample.picker_result.ActivityResults
import com.greentoad.turtlebody.docpicker.ui.components.ActivityLibMain
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.File
import java.io.Serializable

class ActivityHome : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initButton()
    }

    private fun initButton() {
        activity_main_doc_picker.setOnClickListener { showAlert(true) }
        activity_main_all_picker.setOnClickListener { showAlert(false) }
    }


    private fun showAlert(isOnlyDoc: Boolean){
        MaterialDialog(this).show {
            customView(R.layout.dialog_view,scrollable = true)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val view = this.getCustomView()
            val singleBtn = view.findViewById<Button>(R.id.activity_home_single_select)
            val multiBtn = view.findViewById<Button>(R.id.activity_home_multi_select)

            singleBtn.setOnClickListener {
                if(isOnlyDoc)
                    startOnlyDocPicker(false)
                else
                    startAllPicker(false)
                this.dismiss()

            }
            multiBtn.setOnClickListener {
                if(isOnlyDoc)
                    startOnlyDocPicker(true)
                else
                    startAllPicker(true)
                this.dismiss()

            }
        }
    }



    @SuppressLint("CheckResult")
    private fun startOnlyDocPicker(isMultiple: Boolean) {
        val config = DocPickerConfig()
            .setShowConfirmationDialog(true)
            .setAllowMultiSelection(isMultiple)
            .setExtArgs(arrayListOf<String>(
                DocPicker.DocTypes.PDF,
                DocPicker.DocTypes.MS_WORD,
                DocPicker.DocTypes.MS_POWERPOINT,
                DocPicker.DocTypes.MS_EXCEL,
                DocPicker.DocTypes.TEXT
            ))

        DocPicker.with(this)
            .setConfig(config)
            .onResult()
            .subscribe({
                info { "here is the list: $it" }
                startActivityShowResult(it,config)
            },{
                info { "error: ${it.printStackTrace()}" }
            })
    }

    @SuppressLint("CheckResult")
    private fun startAllPicker(isMultiple: Boolean) {

        val docs = arrayListOf<String>(
            DocPicker.DocTypes.PDF,
            DocPicker.DocTypes.MS_WORD,
            DocPicker.DocTypes.MS_POWERPOINT,
            DocPicker.DocTypes.MS_EXCEL,
            DocPicker.DocTypes.TEXT,
            DocPicker.DocTypes.AUDIO,
            DocPicker.DocTypes.IMAGE,
            DocPicker.DocTypes.VIDEO)

        val config = DocPickerConfig()
            .setShowConfirmationDialog(true)
            .setAllowMultiSelection(isMultiple)
            .setExtArgs(docs)

        DocPicker.with(this)
            .setConfig(config)
            .onResult()
            .subscribe({
                info { "here is the list: $it" }
                startActivityShowResult(it,config)
            },{
                info { "error: ${it.printStackTrace()}" }
            })
    }

    private fun startActivityShowResult(it: ArrayList<Uri>?, config: DocPickerConfig) {
        val intent = Intent(this, ActivityResults::class.java)
        intent.putExtra(DocPickerConfig.ARG_BUNDLE, config)
        intent.putExtra(ActivityLibMain.B_ARG_URI_LIST,it as Serializable)
        startActivity(intent)
    }
}
