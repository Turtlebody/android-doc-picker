package com.greentoad.turtlebody.docpicker.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.core.Constants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButton()
    }

    private fun initButton() {
        activity_main_picker.setOnClickListener { startPicker() }
    }

    @SuppressLint("CheckResult")
    private fun startPicker() {
        DocPicker.with(this)
            .setConfig(DocPickerConfig()
                .setAllowMultiImages(true)
                .setExtArgs(arrayOf(
                    Constants.ExtensionsArgs.DOCX,
                    Constants.ExtensionsArgs.PDF,
                    Constants.ExtensionsArgs.PPTX
                ))
            )
            .onResult()
            .subscribe({
                info { "here is the list" }
            },{
                info { "error: ${it.printStackTrace()}" }
            })
    }
}
