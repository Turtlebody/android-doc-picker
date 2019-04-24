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
    fun getDocFolderCursor55(context: Context): Cursor? {
        return context.contentResolver.query(Constants.Queries.docQueryUri, Constants.Projection.DOC_FOLDER,
            MediaStore.Files.FileColumns.DATA+" LIKE ?"+ ") GROUP BY (" + MediaStore.Files.FileColumns.PARENT,
            arrayOf("%.docx"), null)
    }


    fun getDocFolderCursor(context: Context,args: Array<String?>): Cursor? {
        //val args: Array<String> = arrayOf("%.docx")
        val where = FileHelper.createOrQueryFromArray("", args, MediaStore.Files.FileColumns.DATA, " LIKE ?")
        info { "where: $where" }
        info { "args: ${args.toList()}" }
        return context.contentResolver.query(Constants.Queries.docQueryUri, Constants.Projection.DOC_FOLDER, where,
            args, null)
    }

    //folderPath: String
    fun getDocFilesInFolderCursordd(context: Context ): Cursor?{
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


    fun getDocFilesInFolderCursorasas(context: Context,folderPath: String ): Cursor?{
        val path = "$folderPath/"
        return context.contentResolver.query(
            Constants.Queries.docQueryUri, Constants.Projection.DOC_FILE,
            MediaStore.Audio.Media.DATA + " LIKE ? AND " + MediaStore.Audio.Media.DATA + " NOT LIKE ? " +" AND "+ MediaStore.Audio.Media.DATA +" LIKE ?",
            arrayOf("$path%", "$path%/%", "%.docx"), MediaStore.Files.FileColumns.TITLE+" ASC")
    }

    fun getDocFilesInFolderCursor(context: Context,folderPath: String ): Cursor?{
        val path = "$folderPath/"
        return context.contentResolver.query(
            Constants.Queries.docQueryUri, Constants.Projection.DOC_FILE,
            MediaStore.Files.FileColumns.DATA + " LIKE ? AND " + MediaStore.Files.FileColumns.DATA + " NOT LIKE ? ",
            arrayOf("$path%", "$path%/%"), MediaStore.Files.FileColumns.TITLE+" ASC")
    }
}