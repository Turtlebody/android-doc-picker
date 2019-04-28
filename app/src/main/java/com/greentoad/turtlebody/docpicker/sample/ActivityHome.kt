package com.greentoad.turtlebody.docpicker.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        activity_main_doc_picker.setOnClickListener { startOnlyDocPicker() }
        activity_main_all_picker.setOnClickListener { startAllPicker() }
    }



    @SuppressLint("CheckResult")
    private fun startOnlyDocPicker() {
        DocPicker.with(this)
            .setConfig(DocPickerConfig()
                .setAllowMultiImages(true)
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
    private fun startAllPicker() {
        DocPicker.with(this)
            .setConfig(DocPickerConfig()
                .setAllowMultiImages(true)
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
