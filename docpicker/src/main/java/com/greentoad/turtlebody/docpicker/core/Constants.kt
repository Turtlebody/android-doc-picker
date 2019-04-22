package com.greentoad.turtlebody.docpicker.core

import android.provider.MediaStore

/**
 * Created by WANGSUN on 27-Mar-19.
 */
object Constants {

    object FileTypes{
        const val MEDIA_TYPE_IMAGE = 501
        const val MEDIA_TYPE_VIDEO = 502
        const val MEDIA_TYPE_AUDIO = 503
        const val MEDIA_TYPE_DOC = 504
    }

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
        const val IMAGE_VIDEO_FOLDER = 101
        const val AUDIO_FOLDER = 102

        const val IMAGE_LIST = 103
        const val VIDEO_LIST = 104
        const val AUDIO_LIST = 105
    }
}