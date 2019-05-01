package com.greentoad.turtlebody.docpicker.core

import android.provider.MediaStore
import android.text.TextUtils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by WANGSUN on 17-Apr-19.
 */
object FileHelper : AnkoLogger{
    fun createOrQueryFromArray(pWhere: String, args: Array<String?>, column: String, operator:String = "=?"): String {
        //where
        if(args.isEmpty())return pWhere
        info { "where 1 size: "+args.size}

        var where = "$column$operator"
        if(args.size>1){
            for(indices in 1 until args.size){
                info { "where index: $indices" }
                where= "$where OR $column$operator"
            }
        }

        if(!TextUtils.isEmpty(pWhere)){
            info { "where 2: $pWhere" }
            where = "$pWhere AND ($where)"
        }

        return where+ ") GROUP BY (" + MediaStore.Files.FileColumns.PARENT
    }
}