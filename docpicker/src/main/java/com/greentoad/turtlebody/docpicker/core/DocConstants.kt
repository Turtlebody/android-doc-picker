package com.greentoad.turtlebody.docpicker.core

import android.provider.MediaStore
import com.greentoad.turtlebody.docpicker.DocPicker

/**
 * Created by WANGSUN on 24-Apr-19.
 */
object DocConstants {

    object Queries{
        val docQueryUri = MediaStore.Files.getContentUri("external")
    }

    object Projection{
        /**
         * AudioModel projections
         */
        val DOC_FOLDER = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.PARENT,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            "COUNT(" + MediaStore.Files.FileColumns.DATA + ") AS dataCount")

        val DOC_FILE = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE)
    }


    object Intent{
        const val ACTIVITY_LIB_MAIN = 201
    }

    object Fragment{
        const val DOC_FOLDER = 101
        const val DOC_LIST = 102
        const val DOC_FILTER = 103
    }




    fun docTypeMapLabel(): HashMap<String,String>{
        val map = HashMap<String,String>()
        map[DocPicker.DocTypes.PDF] = "pdf"
        map[DocPicker.DocTypes.MS_WORD] = "doc"
        map[DocPicker.DocTypes.MS_EXCEL] = "xls"
        map[DocPicker.DocTypes.MS_POWERPOINT] = "ppt"
        map[DocPicker.DocTypes.TEXT] = "txt"
        map[DocPicker.DocTypes.IMAGE] = "img"
        map[DocPicker.DocTypes.AUDIO] = "aud"
        map[DocPicker.DocTypes.VIDEO] = "vid"
        return map
    }


    fun getExt(docType: String): ArrayList<String>{
        when(docType){
            DocPicker.DocTypes.PDF ->{
                return arrayListOf("pdf")
            }
            DocPicker.DocTypes.MS_WORD ->{
                return arrayListOf("doc","docx")
            }
            DocPicker.DocTypes.MS_POWERPOINT ->{
                return arrayListOf("ppt","pptx","key","odp","pps")
            }
            DocPicker.DocTypes.MS_EXCEL ->{
                return arrayListOf("xls","xlsx","ods","xlr")
            }
            DocPicker.DocTypes.TEXT ->{
                return arrayListOf("txt","log")
            }
            DocPicker.DocTypes.IMAGE ->{
                return arrayListOf("jpg","jpeg","ico","ai","bmp","gif","png","ps","psd","svg","tif","tiff")
            }
            DocPicker.DocTypes.VIDEO ->{
                return arrayListOf("3g2","3gp","avi","flv","h264","m4v","mkv","mov","mp4","mpg","mpeg","rm","swf","vob","wmv")
            }
            DocPicker.DocTypes.AUDIO ->{
                return arrayListOf("aif","cda","mid","midi","mp3","mpa","ogg","wav","wma","wpl")
            }
            else -> return arrayListOf("pdf")
        }
    }

}