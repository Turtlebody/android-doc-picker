package com.greentoad.turtlebody.docpicker.core

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


/**
 * Created by WANGSUN on 27-Mar-19.
 */
object CursorHelper: AnkoLogger {


    /**
     * cursor for doc
     */
    fun getDocFolderCursor(context: Context): Cursor? {
        return context.contentResolver.query(Constants.Queries.docQueryUri, Constants.Projection.DOC_FOLDER,
            MediaStore.Files.FileColumns.DATA+" LIKE ?"+" AND "+
                    MediaStore.Files.FileColumns.DATA+" NOT LIKE ?"+ ") GROUP BY (" + MediaStore.Files.FileColumns.PARENT,
            arrayOf("%.docx","%/.cache/%"), null)
    }


    fun getDocFolderCursor4(context: Context): Cursor? {
        val args = arrayOf("%.docx")
        val where = FileHelper.createOrQueryFromArray("", args, MediaStore.Files.FileColumns.DATA, " LIKE ?")
        return context.contentResolver.query(Constants.Queries.docQueryUri, Constants.Projection.DOC_FOLDER, where, args, null)
    }

    //folderPath: String
    fun getDocFilesInFolderCursor(context: Context ): Cursor?{
    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("%.docx")
    info { "mimeType: $mimeType" }
        //val path = "$folderPath/"
        return context.contentResolver.query(
            Constants.Queries.docQueryUri,
            Constants.Projection.DOC_FILE,
//            MediaStore.Audio.Media.DATA + " LIKE ? AND " + MediaStore.Audio.Media.DATA + " NOT LIKE ? " +" AND "+ MediaStore.Audio.Media.MIME_TYPE +" LIKE ?",
//                arrayOf("$path%", "$path%/%", "audio%"),
             MediaStore.Files.FileColumns.MIME_TYPE +" LIKE ?",
            arrayOf(mimeType),
            MediaStore.Files.FileColumns.TITLE+" ASC")
    }


    fun getDocFilesInFolderCursor2(context: Context,folderPath: String ): Cursor?{
        val path = "$folderPath/"
        return context.contentResolver.query(
            Constants.Queries.docQueryUri, Constants.Projection.DOC_FILE,
            MediaStore.Audio.Media.DATA + " LIKE ? AND " + MediaStore.Audio.Media.DATA + " NOT LIKE ? " +" AND "+ MediaStore.Audio.Media.MIME_TYPE +" LIKE ?",
            arrayOf("$path%", "$path%/%", "audio%"), MediaStore.Files.FileColumns.TITLE+" ASC")
    }
}