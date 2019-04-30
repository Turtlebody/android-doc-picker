package com.greentoad.turtlebody.docpicker.core

import com.greentoad.turtlebody.docpicker.labels.DocLabelSet
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.Serializable

/**
 * Created by WANGSUN on 29-Mar-19.
 */
class DocPickerConfig: AnkoLogger, Serializable {
    var mShowConfirmationDialog: Boolean = false
    var mAllowMultiImages: Boolean = false
    var mExtArgs: Array<String?> = arrayOf()
    var mDocLabelSet = DocLabelSet()

    var mDocTypes: ArrayList<String> = arrayListOf()
    var mUserSelectedDocTypes: ArrayList<String> = arrayListOf()

    companion object {
        val ARG_BUNDLE = javaClass.canonicalName + ".bundle_arg"
    }

    init {

        mUserSelectedDocTypes.add(DocConstants.DocTypes.PDF)
        mUserSelectedDocTypes.add(DocConstants.DocTypes.MS_WORD)
        mUserSelectedDocTypes.add(DocConstants.DocTypes.MS_POWERPOINT)
        mUserSelectedDocTypes.add(DocConstants.DocTypes.MS_EXCEL)
        mUserSelectedDocTypes.add(DocConstants.DocTypes.TEXT)

        mDocTypes.add(DocConstants.DocTypes.PDF)
        mDocTypes.add(DocConstants.DocTypes.MS_WORD)
        mDocTypes.add(DocConstants.DocTypes.MS_POWERPOINT)
        mDocTypes.add(DocConstants.DocTypes.MS_EXCEL)
        mDocTypes.add(DocConstants.DocTypes.TEXT)

        //since array<String> is static
        val size = DocConstants.getExt(DocConstants.DocTypes.PDF).size +
                DocConstants.getExt(DocConstants.DocTypes.MS_WORD).size +
                DocConstants.getExt(DocConstants.DocTypes.MS_POWERPOINT).size +
                DocConstants.getExt(DocConstants.DocTypes.MS_EXCEL).size+
                DocConstants.getExt(DocConstants.DocTypes.TEXT).size

        mExtArgs = arrayOfNulls(size)

        for(i in 0 until DocConstants.getExt(DocConstants.DocTypes.PDF).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocConstants.DocTypes.PDF)[i]
        }
        for(i in 0 until DocConstants.getExt(DocConstants.DocTypes.MS_WORD).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocConstants.DocTypes.MS_WORD)[i]
        }
        for(i in 0 until DocConstants.getExt(DocConstants.DocTypes.MS_POWERPOINT).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocConstants.DocTypes.MS_POWERPOINT)[i]
        }
        for(i in 0 until DocConstants.getExt(DocConstants.DocTypes.MS_EXCEL).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocConstants.DocTypes.MS_EXCEL)[i]
        }
        for(i in 0 until DocConstants.getExt(DocConstants.DocTypes.TEXT).size){
            mExtArgs[i] = "%."+DocConstants.getExt(DocConstants.DocTypes.TEXT)[i]
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
     * Allow multiple selection
     */
    fun setAllowMultiImages(value: Boolean): DocPickerConfig {
        mAllowMultiImages = value
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

    fun getCustomExtArgs(value: ArrayList<String>): Array<String?>{
        mUserSelectedDocTypes = value

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