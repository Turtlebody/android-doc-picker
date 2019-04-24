package com.greentoad.turtlebody.docpicker.core

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

}