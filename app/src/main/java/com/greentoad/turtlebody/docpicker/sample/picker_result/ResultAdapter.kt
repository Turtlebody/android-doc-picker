package com.greentoad.turtlebody.docpicker.sample.picker_result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoLogger
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.labels.DocLabelSet
import com.greentoad.turtlebody.docpicker.sample.R
import kotlinx.android.synthetic.main.item_result.view.*
import org.jetbrains.anko.info
import java.io.File




/**
 * Created by WANGSUN on 26-Mar-19.
 */
class ResultAdapter: RecyclerView.Adapter<ResultAdapter.DocVewHolder>(), AnkoLogger {
    private var mData: MutableList<Uri> = arrayListOf()
    private var mPickerConfig: DocPickerConfig = DocPickerConfig()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocVewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)
        return DocVewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DocVewHolder, position: Int) {
        holder.bind(mData[position])
    }


    fun setData(pData: MutableList<Uri>){
        mData = pData
        notifyDataSetChanged()
    }

    fun setPickerConfig(config: DocPickerConfig){
        mPickerConfig = config
    }
    inner class DocVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: Uri){

            info { "ext: ${File(pData.path).extension}" }
            val file = File(pData.path)

            setDrawableForMime(itemView,file.path)

            val size = (file.length()).toString()

            itemView.item_result_doc_name.text = file.name
        }

        private fun setDrawableForMime(itemView: View, filePath: String) {

            var extType  = String()

            val i = filePath.lastIndexOf('.')
            if (i > 0 && i< filePath.length-1) {
                extType = filePath.substring(i + 1)
            }

            val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.tb_doc_picker_dr_rect_round_red_doc_background)

            info { "extType: $extType" }
            info { "res: ${DocLabelSet().getLabelForExt(extType)}" }

            val label = mPickerConfig.mDocLabelSet.getLabelForExt(extType)
            mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,label.colorRes), PorterDuff.Mode.SRC)
            itemView.item_result_doc_ext.text = label.text
            itemView.item_result_doc_ext.background = mDrawable
        }
    }
}