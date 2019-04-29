package com.greentoad.turtlebody.docpicker.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.core.DocConstants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ActivityHome : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        DocPicker.with(this)
            .setConfig(DocPickerConfig()
                .setAllowMultiImages(isMultiple)
                .setExtArgs(arrayListOf<String>(
                    DocConstants.DocTypes.PDF,
                    DocConstants.DocTypes.MS_WORD,
                    DocConstants.DocTypes.MS_POWERPOINT,
                    DocConstants.DocTypes.MS_EXCEL,
                    DocConstants.DocTypes.TEXT
                ))
            )
            .onResult()
            .subscribe({
                info { "here is the list: $it" }
            },{
                info { "error: ${it.printStackTrace()}" }
            })
    }

    @SuppressLint("CheckResult")
    private fun startAllPicker(isMultiple: Boolean) {
        DocPicker.with(this)
            .setConfig(DocPickerConfig()
                .setAllowMultiImages(isMultiple)
                .setExtArgs(arrayListOf<String>(
                    DocConstants.DocTypes.PDF,
                    DocConstants.DocTypes.MS_WORD,
                    DocConstants.DocTypes.MS_POWERPOINT,
                    DocConstants.DocTypes.MS_EXCEL,
                    DocConstants.DocTypes.TEXT,
                    DocConstants.DocTypes.AUDIO,
                    DocConstants.DocTypes.IMAGE,
                    DocConstants.DocTypes.VIDEO
                ))
            )
            .onResult()
            .subscribe({
                info { "here is the list: $it" }
            },{
                info { "error: ${it.printStackTrace()}" }
            })
    }
}
