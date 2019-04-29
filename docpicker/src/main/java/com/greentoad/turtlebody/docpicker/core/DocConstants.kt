package com.greentoad.turtlebody.docpicker.core

import android.content.Context
import androidx.core.content.ContextCompat
import com.greentoad.turtlebody.docpicker.R

/**
 * Created by WANGSUN on 24-Apr-19.
 */
object DocConstants {

    object DocTypes{
        const val PDF = "pdf"
        const val MS_WORD = "word"
        const val MS_EXCEL = "excel"
        const val MS_POWERPOINT = "powerpoint"
        const val TEXT = "text"

        const val IMAGE = "image"
        const val AUDIO = "audio"
        const val VIDEO = "video"
    }


    fun docTypeMaps(): HashMap<String,String>{
        val map = HashMap<String,String>()
        map[DocTypes.PDF] = "pdf"
        map[DocTypes.MS_WORD] = "doc"
        map[DocTypes.MS_EXCEL] = "xls"
        map[DocTypes.MS_POWERPOINT] = "ppt"
        map[DocTypes.TEXT] = "txt"
        map[DocTypes.IMAGE] = "jpg"
        map[DocTypes.AUDIO] = "mp3"
        map[DocTypes.VIDEO] = "mp4"
        return map
    }

    fun docTypeMapColor(context: Context): HashMap<String,Int>{
        val map = HashMap<String,Int>()
        map[DocTypes.PDF] = ContextCompat.getColor(context, R.color.tb_doc_picker_pdf)
        map[DocTypes.MS_WORD] = ContextCompat.getColor(context, R.color.tb_doc_picker_doc)
        map[DocTypes.MS_EXCEL] = ContextCompat.getColor(context, R.color.tb_doc_picker_xls)
        map[DocTypes.MS_POWERPOINT] = ContextCompat.getColor(context, R.color.tb_doc_picker_ppt)
        map[DocTypes.TEXT] = ContextCompat.getColor(context, R.color.tb_doc_picker_txt)
        map[DocTypes.IMAGE] = ContextCompat.getColor(context, R.color.tb_doc_picker_image)
        map[DocTypes.AUDIO] = ContextCompat.getColor(context, R.color.tb_doc_picker_audio)
        map[DocTypes.VIDEO] = ContextCompat.getColor(context, R.color.tb_doc_picker_video)

        return map
    }

    fun docTypeMapLightColor(context: Context): HashMap<String,Int>{
        val map = HashMap<String,Int>()
        map[DocTypes.PDF] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_pdf)
        map[DocTypes.MS_WORD] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_doc)
        map[DocTypes.MS_EXCEL] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_xls)
        map[DocTypes.MS_POWERPOINT] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_ppt)
        map[DocTypes.TEXT] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_txt)
        map[DocTypes.IMAGE] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_image)
        map[DocTypes.AUDIO] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_audio)
        map[DocTypes.VIDEO] = ContextCompat.getColor(context, R.color.tb_doc_picker_light_video)
        return map
    }



    fun getExt(docType: String): ArrayList<String>{
        when(docType){
            DocTypes.PDF ->{
                return arrayListOf("pdf")
            }
            DocTypes.MS_WORD ->{
                return arrayListOf("doc","docx")
            }
            DocTypes.MS_POWERPOINT ->{
                return arrayListOf("ppt","pptx","key","odp","pps")
            }
            DocTypes.MS_EXCEL ->{
                return arrayListOf("xls","xlsx","ods","xlr")
            }
            DocTypes.TEXT ->{
                return arrayListOf("txt","log")
            }
            DocTypes.IMAGE ->{
                return arrayListOf("jpg","jpeg","ico","ai","bmp","gif","png","ps","psd","svg","tif","tiff")
            }
            DocTypes.VIDEO ->{
                return arrayListOf("3g2","3gp","avi","flv","h264","m4v","mkv","mov","mp4","mpg","mpeg","rm","swf","vob","wmv")
            }
            DocTypes.AUDIO ->{
                return arrayListOf("aif","cda","mid","midi","mp3","mpa","ogg","wav","wma","wpl")
            }
            else -> return arrayListOf("pdf")
        }
    }




    object DocTypes2{
        const val PDF = "pdf"
        const val MS_WORD = "word"
        const val MS_EXCEL = "excel"
        const val MS_POWERPOINT = "powerpoint"
        const val TEXT = "text"

        const val AI = "AI"
        const val AE = "AE"
        const val APK = "APK"
        const val JPG = "JPG"
        const val MP3 = "MP3"
        const val MP4 = "MP4"
        const val PNG = "PNG"
        const val PSD = "PSD"
        const val XD = "XD"
        const val ZIP = "ZIP"
        const val RAR = "RAR"
    }

    /*fun docTypeMapColor2(context: Context): HashMap<String,Int>{
        val map = HashMap<String,Int>()
        map[DocTypes2.PDF] = ContextCompat.getColor(context, R.color.tb_doc_pdf)
        map[DocTypes2.MS_WORD] = ContextCompat.getColor(context, R.color.tb_doc_doc)
        map[DocTypes2.MS_EXCEL] = ContextCompat.getColor(context, R.color.tb_doc_xls)
        map[DocTypes2.MS_POWERPOINT] = ContextCompat.getColor(context, R.color.tb_doc_ppt)
        map[DocTypes2.AI] = ContextCompat.getColor(context, R.color.tb_doc_ai)
        map[DocTypes2.AE] = ContextCompat.getColor(context, R.color.tb_doc_ae)
        map[DocTypes2.APK] = ContextCompat.getColor(context, R.color.tb_doc_apk)
        map[DocTypes2.MP3] = ContextCompat.getColor(context, R.color.tb_doc_mp3)
        map[DocTypes2.JPG] = ContextCompat.getColor(context, R.color.tb_doc_jpg)
        map[DocTypes2.TEXT] = ContextCompat.getColor(context, R.color.tb_doc_txt)
        map[DocTypes2.MP4] = ContextCompat.getColor(context, R.color.tb_doc_mp4)
        map[DocTypes2.PNG] = ContextCompat.getColor(context, R.color.tb_doc_png)
        map[DocTypes2.PSD] = ContextCompat.getColor(context, R.color.tb_doc_psd)
        map[DocTypes2.XD] = ContextCompat.getColor(context, R.color.tb_doc_xd)
        map[DocTypes2.ZIP] = ContextCompat.getColor(context, R.color.tb_doc_zip)
        map[DocTypes2.RAR] = ContextCompat.getColor(context, R.color.tb_doc_rar)
        return map
    }

    fun docTypeMapLightColor2(context: Context): HashMap<String,Int>{
        val map = HashMap<String,Int>()
        map[DocTypes2.PDF] = ContextCompat.getColor(context, R.color.tb_doc_light_pdf)
        map[DocTypes2.MS_WORD] = ContextCompat.getColor(context, R.color.tb_doc_light_doc)
        map[DocTypes2.MS_EXCEL] = ContextCompat.getColor(context, R.color.tb_doc_light_xls)
        map[DocTypes2.MS_POWERPOINT] = ContextCompat.getColor(context, R.color.tb_doc_light_ppt)
        map[DocTypes2.AI] = ContextCompat.getColor(context, R.color.tb_doc_light_ai)
        map[DocTypes2.AE] = ContextCompat.getColor(context, R.color.tb_doc_light_ae)
        map[DocTypes2.APK] = ContextCompat.getColor(context, R.color.tb_doc_light_apk)
        map[DocTypes2.MP3] = ContextCompat.getColor(context, R.color.tb_doc_light_mp3)
        map[DocTypes2.JPG] = ContextCompat.getColor(context, R.color.tb_doc_light_jpg)
        map[DocTypes2.TEXT] = ContextCompat.getColor(context, R.color.tb_doc_light_txt)
        map[DocTypes2.MP4] = ContextCompat.getColor(context, R.color.tb_doc_light_mp4)
        map[DocTypes2.PNG] = ContextCompat.getColor(context, R.color.tb_doc_light_png)
        map[DocTypes2.PSD] = ContextCompat.getColor(context, R.color.tb_doc_light_psd)
        map[DocTypes2.XD] = ContextCompat.getColor(context, R.color.tb_doc_light_xd)
        map[DocTypes2.ZIP] = ContextCompat.getColor(context, R.color.tb_doc_light_zip)
        map[DocTypes2.RAR] = ContextCompat.getColor(context, R.color.tb_doc_light_rar)
        return map
    }*/

}