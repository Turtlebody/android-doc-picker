package com.greentoad.turtlebody.docpicker.core

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import com.greentoad.turtlebody.docpicker.ui.components.file.DocModel
import com.greentoad.turtlebody.docpicker.ui.components.folder.DocFolder
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.File
import java.util.*


object FileManager : AnkoLogger {
    /**
     * For Doc
     */
    fun fetchAudioFolderList(context: Context,args: Array<String?>): ArrayList<DocFolder> {
        val folders = ArrayList<DocFolder>()
        val folderFileCountMap = HashMap<String, Int>()

        val projection = DocConstants.Projection.DOC_FOLDER

        // Create the cursor pointing to the SDCard
        val cursor = CursorHelper.getDocFolderCursor(context,args)

        cursor?.let {
            info { "doc folder query size: "+ cursor.count }

            val columnIndexFolderId = it.getColumnIndexOrThrow(projection[0])
            val columnIndexCount = it.getColumnIndexOrThrow("dataCount")
            //val columnIndexFolderParent = it.getColumnIndexOrThrow(projection[1])
            val columnIndexFilePath = it.getColumnIndexOrThrow(projection[2])
            //val columnIndexDisplayName = it.getColumnIndexOrThrow(projection[3])

            while (it.moveToNext()) {
                val folderId = it.getString(columnIndexFolderId)
                val audioCounts = it.getInt(columnIndexCount)
                if (folderFileCountMap.containsKey(folderId)) {
                    folderFileCountMap[folderId] = folderFileCountMap[folderId]!! + audioCounts
                } else {
                    val folder = DocFolder(
                        folderId, File(it.getString(columnIndexFilePath)).parentFile.name,
                        File(it.getString(columnIndexFilePath)).parentFile.path, 0
                    )
                    folders.add(folder)
                    folderFileCountMap[folderId] = audioCounts
                }
            }
            for (fdr in folders) {
                fdr.contentCount = folderFileCountMap[fdr.id]!!
            }
            cursor.close()
        }
        return folders
    }



    fun getDocFilesInFolder(context: Context,folderPath: String): ArrayList<DocModel> {
        val fileItems = ArrayList<DocModel>()
        val projection = DocConstants.Projection.DOC_FILE

        // Create the cursor pointing to the SDCard
        val cursor: Cursor? = CursorHelper.getDocFilesInFolderCursor(context,folderPath)

        cursor?.let {
            info { "doc query size: "+ cursor.count }
            val columnIndexDocId = it.getColumnIndexOrThrow(projection[0])
            val columnIndexDocTitle = it.getColumnIndexOrThrow(projection[1])
            val columnIndexDocName = it.getColumnIndexOrThrow(projection[2])
            val columnIndexDocSize = it.getColumnIndexOrThrow(projection[3])
            val columnIndexDocPath = it.getColumnIndexOrThrow(projection[4])
            val columnIndexDocMimeType = it.getColumnIndexOrThrow(projection[5])


            while (it.moveToNext()) {
                var name = it.getString(columnIndexDocName)

                info { "title: ${it.getString(columnIndexDocTitle)}" }
                info { "name: ${it.getString(columnIndexDocName)}" }
                if(name==null){
                   name =  it.getString(columnIndexDocTitle)
                }
                if(name==null){
                 name = ""
                }
                val fileItem = DocModel(
                    it.getString(columnIndexDocId),
                    name,
                    it.getInt(columnIndexDocSize),
                    it.getString(columnIndexDocPath),
                    //it.getString(columnIndexAudioArtist),
                    it.getString(columnIndexDocMimeType),
                    false
                )
                fileItems.add(fileItem)

            }
            cursor.close()
        }
        info { "docFiles size: ${fileItems.size}" }
        info { "docFiles: $fileItems" }
        return fileItems
    }




    fun getContentUri(context: Context, newFile: File): Uri {
        val SHARED_PROVIDER_AUTHORITY = context.packageName+ ".greentoad.turtlebody.docprovider"
        info { "id: $SHARED_PROVIDER_AUTHORITY" }
        return getUriForFile(context, SHARED_PROVIDER_AUTHORITY, newFile)
    }
}

