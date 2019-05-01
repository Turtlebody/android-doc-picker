package com.greentoad.turtlebody.docpicker.core

import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.labels.DocLabelSet
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.Serializable

/**
 * Created by WANGSUN on 29-Mar-19.
 */
class DocPickerConfig: AnkoLogger, Serializable {

    //todo: disable set option for below variables
    var mShowConfirmationDialog: Boolean = false
    var mAllowMultiSelection: Boolean = false

    var mExtArgs: Array<String?> = arrayOf()
    var mDocLabelSet = DocLabelSet()

    var mDocTypes: ArrayList<String> = arrayListOf()
    //subset of mDoctype
    var mUserSelectedDocTypes: ArrayList<String> = arrayListOf()
    //upto here

    companion object {
        val ARG_BUNDLE = javaClass.canonicalName + ".bundle_arg"
    }

    init {

        mUserSelectedDocTypes.add(DocPicker.DocTypes.PDF)
        mUserSelectedDocTypes.add(DocPicker.DocTypes.MS_WORD)
        mUserSelectedDocTypes.add(DocPicker.DocTypes.MS_POWERPOINT)
        mUserSelectedDocTypes.add(DocPicker.DocTypes.MS_EXCEL)
        mUserSelectedDocTypes.add(DocPicker.DocTypes.TEXT)

        mDocTypes.add(DocPicker.DocTypes.PDF)
        mDocTypes.add(DocPicker.DocTypes.MS_WORD)
        mDocTypes.add(DocPicker.DocTypes.MS_POWERPOINT)
        mDocTypes.add(DocPicker.DocTypes.MS_EXCEL)
        mDocTypes.add(DocPicker.DocTypes.TEXT)

        //since array<String> is static
        val size = DocConstants.getExt(DocPicker.DocTypes.PDF).size +
                DocConstants.getExt(DocPicker.DocTypes.MS_WORD).size +
                DocConstants.getExt(DocPicker.DocTypes.MS_POWERPOINT).size +
                DocConstants.getExt(DocPicker.DocTypes.MS_EXCEL).size+
                DocConstants.getExt(DocPicker.DocTypes.TEXT).size

        mExtArgs = arrayOfNulls(size)

        for(i in 0 until DocConstants.getExt(DocPicker.DocTypes.PDF).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocPicker.DocTypes.PDF)[i]
        }
        for(i in 0 until DocConstants.getExt(DocPicker.DocTypes.MS_WORD).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocPicker.DocTypes.MS_WORD)[i]
        }
        for(i in 0 until DocConstants.getExt(DocPicker.DocTypes.MS_POWERPOINT).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocPicker.DocTypes.MS_POWERPOINT)[i]
        }
        for(i in 0 until DocConstants.getExt(DocPicker.DocTypes.MS_EXCEL).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocPicker.DocTypes.MS_EXCEL)[i]
        }
        for(i in 0 until DocConstants.getExt(DocPicker.DocTypes.TEXT).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocPicker.DocTypes.TEXT)[i]
        }
    }

    /**
     *  Show confirmation dialog after selecting file (works only for single file selection)
     */
    fun setShowConfirmationDialog(value: Boolean): DocPickerConfig {
        mShowConfirmationDialog = value
        return this
    }

    /**
     * Allow multiple file selection
     */
    fun setAllowMultiSelection(value: Boolean): DocPickerConfig {
        mAllowMultiSelection = value
        return this
    }



    fun setExtArgs(value: ArrayList<String>): DocPickerConfig{
        mDocTypes = value
        mUserSelectedDocTypes = value


        var size = 0
        for(i in 0 until value.size){
            size += DocConstants.getExt(value[i]).size
            info { "size of array: $size" }
        }
        info { "size of new array: $size" }

        mExtArgs = arrayOfNulls(size)
        var k = 0
        for(i in value){
            for(j in DocConstants.getExt(i)){
                mExtArgs[k] = "%.$j"
                mDocLabelSet.attachExtensions(j)
                info { "args: ${mExtArgs[k]}" }
                k++
            }
        }
        return this
    }

    fun setUserSelectedDocTypes(value: ArrayList<String>){
        mUserSelectedDocTypes = value
    }

    fun getUserSelectedExtArgs(value: ArrayList<String>): Array<String?>{
        var size = 0
        for(i in 0 until value.size){
            size += DocConstants.getExt(value[i]).size
            info { "size of array: $size" }
        }
        info { "size of new array: $size" }

        val extArgs = arrayOfNulls<String>(size)
        var k = 0
        for(i in value){
            for(j in DocConstants.getExt(i)){
                extArgs[k] = "%.$j"
                info { "args: ${extArgs[k]}" }
                k++
            }
        }
        return extArgs
    }
}