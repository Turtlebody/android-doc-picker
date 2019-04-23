package com.greentoad.turtlebody.docpicker.ui.components.file

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.greentoad.turtlebody.docpicker.R
import kotlinx.android.synthetic.main.tb_doc_picker_item_doc.view.*
import org.jetbrains.anko.AnkoLogger
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter




/**
 * Created by WANGSUN on 26-Mar-19.
 */
class DocAdapter: RecyclerView.Adapter<DocAdapter.DocVewHolder>(), AnkoLogger {
    private var mData: MutableList<DocModel> = arrayListOf()
    private var mOnDocClickListener: OnDocClickListener? = null
    var mShowCheckBox: Boolean = false
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocVewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tb_doc_picker_item_doc, parent, false)
        return DocVewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DocVewHolder, position: Int) {
        holder.bind(mData[position])
    }


    fun setListener(listener : OnDocClickListener){
        mOnDocClickListener = listener
    }

    fun setData(pData: MutableList<DocModel>){
        mData = pData
        notifyDataSetChanged()
    }

    fun updateIsSelected(pData: DocModel){
        val pos = mData.indexOf(pData)
        if(pos>=0){
            mData[pos] = pData
            notifyItemChanged(pos)
        }
    }

    inner class DocVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: DocModel){

            setDrawableForMime(itemView, pData.mimeType,pData.filePath)

            itemView.tb_doc_picker_item_doc_ivc.isChecked = pData.isSelected
            val size = (pData.size/1000).toString()

            itemView.tb_doc_picker_doc_name.text = pData.name
            itemView.tb_doc_picker_doc_size.text = "$size KB"

            itemView.setOnClickListener {
                mOnDocClickListener?.onDocCheck(pData)
            }

            if(!mShowCheckBox){
                itemView.tb_doc_picker_item_doc_ivc.visibility = View.GONE
            }
            else{
                itemView.tb_doc_picker_item_doc_ivc.visibility = View.VISIBLE
            }
        }

        private fun setDrawableForMime(itemView: View, mimeType: String?, filePath: String) {

            var extType = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

            if(extType==null){
                val i = filePath.lastIndexOf('.')
                if (i > 0 && i< filePath.length-1) {
                    extType = filePath.substring(i + 1)
                }
            }

            val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.dr_rect_round_red_doc_background)

            when(extType){
                "pdf"-> {
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_red), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "pdf"
                }
                "doc"->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_dark_blue), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "doc"
                }
                "docx"->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_dark_blue), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "doc"
                }
                "ppt" ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_teal), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "ppt"
                }
                "pptx" ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_teal), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "ppt"
                }
                else ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_red), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_file_ext.text = "other"
                }
            }
            itemView.tb_doc_picker_item_doc_file_ext.background = mDrawable
        }
    }


    interface OnDocClickListener {
        fun onDocCheck(pData: DocModel)
    }
}