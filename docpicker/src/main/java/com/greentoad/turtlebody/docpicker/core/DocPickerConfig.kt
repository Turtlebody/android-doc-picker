package com.greentoad.turtlebody.docpicker.core

import java.io.Serializable

/**
 * Created by WANGSUN on 29-Mar-19.
 */
class DocPickerConfig: Serializable {
    var mShowConfirmationDialog: Boolean = false
    var mAllowMultiImages: Boolean = false
    var mExtArgs: Array<String> = arrayOf("%.pdf", "%.doc","%.docx", "%.xls", "%.xlsx", "%.ppt", "%.pptx")

    companion object {
        val ARG_BUNDLE = javaClass.canonicalName + ".bundle_arg"
    }

    /**
     *  Show confirmation dialog after selecting file (works only for single file selection)
     */
    fun setShowConfirmationDialog(value: Boolean): DocPickerConfig {
        mShowConfirmationDialog = value
        return this
    }

    /**
     * Allow multiple selection
     */
    fun setAllowMultiImages(value: Boolean): DocPickerConfig {
        mAllowMultiImages = value
        return this
    }


    fun setExtArgs(value: Array<String>): DocPickerConfig{
        mExtArgs = value
        return this
    }
}