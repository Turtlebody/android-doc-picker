package com.greentoad.turtlebody.docpicker.labels

import com.greentoad.turtlebody.docpicker.R
import java.io.Serializable

/**
 * Created by niraj on 29-04-2019.
 */
class DocLabelSet(vararg docLabels: DocLabel):Serializable {

    var labels: HashMap<String, DocLabel> = hashMapOf()

    init {
        for (doc in docLabels){
            this.labels[doc.extType] = doc
        }
    }

    fun attachExtensions(vararg extName: String){
        for (ext in extName){
            if(!labels.contains(ext)){
                addExtension(ext)
            }
        }
    }

    private fun addExtension(extName: String) {
        val id = DocLabelConstants.getDocLabelGroupId(extName)
        val ext = DocLabelConstants.getExtLabel(id, extName)
        val color = DocLabelConstants.getColorRes(id)
        val colorLight = DocLabelConstants.getColorLightRes(id)
        this.labels[extName] = DocLabel(color,colorLight,ext, extName)

    }

    fun getLabelForExt(extName: String): DocLabel {
        if(this.labels.contains(extName)){
            return this.labels[extName]!!
        }

        val ext =
             if (extName.length > 3) {
                 extName.substring(0, 3)
            } else {
                 extName
            }
        return DocLabel(R.color.md_grey_600,R.color.md_grey_50, ext, extName)
    }
}