package com.greentoad.turtlebody.docpicker.core

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


/**
 * Created by WANGSUN on 27-Mar-19.
 */
object CursorHelper: AnkoLogger {


    /**
     * cursor for doc
     */
    fun getDocFolderCursor(context: Context,args: Array<String?>): Cursor? {
        //val args: Array<String> = arrayOf("%.docx")
        val where = FileHelper.createOrQueryFromArray("", args, MediaStore.Files.FileColumns.DATA, " LIKE ?")
        info { "where: $where" }
        info { "args: ${args.toList()}" }
        return context.contentResolver.query(DocConstants.Queries.docQueryUri, DocConstants.Projection.DOC_FOLDER, where,
            args, null)
    }


    fun getDocFilesInFolderCursor(context: Context,folderPath: String ): Cursor?{
        val path = "$folderPath/"
        return context.contentResolver.query(
            DocConstants.Queries.docQueryUri, DocConstants.Projection.DOC_FILE,
            MediaStore.Files.FileColumns.DATA + " LIKE ? AND " + MediaStore.Files.FileColumns.DATA + " NOT LIKE ? ",
            arrayOf("$path%", "$path%/%"), MediaStore.Files.FileColumns.TITLE+" ASC")
    }
}