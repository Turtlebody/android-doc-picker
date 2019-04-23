package com.greentoad.turtlebody.docpicker.ui.common.doc_filter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.greentoad.turtlebody.docpicker.R
import kotlinx.android.synthetic.main.tb_doc_picker_item_doc_filter.view.*
import org.jetbrains.anko.AnkoLogger


/**
 * Created by WANGSUN on 26-Mar-19.
 */
class DocFilterAdapter: RecyclerView.Adapter<DocFilterAdapter.DocVewHolder>(), AnkoLogger {
    private var mData: MutableList<DocTypeModel> = arrayListOf()
    private var mOnDocClickListener: OnDocClickListener? = null
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocVewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tb_doc_picker_item_doc_filter, parent, false)
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

    fun setData(pData: MutableList<DocTypeModel>){
        mData = pData
        notifyDataSetChanged()
    }

    fun updateIsSelected(pData: DocTypeModel){
        val pos = mData.indexOf(pData)
        if(pos>=0){
            mData[pos] = pData
            notifyItemChanged(pos)
        }
    }

    inner class DocVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: DocTypeModel){

            setDrawableForMime(itemView, pData.docType)

            itemView.tb_doc_picker_item_doc_filter_ivc.isChecked = pData.isSelected

            itemView.tb_doc_picker_doc_filter_doc_type.text = pData.docType

            itemView.setOnClickListener {
                mOnDocClickListener?.onDocCheck(pData)
            }
        }

        private fun setDrawableForMime(itemView: View, fileType: String) {

            val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.dr_rect_round_red_doc_background)

            when(fileType.toLowerCase()){
                "pdf"-> {
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_red), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "pdf"
                }
                "word"->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_dark_blue), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "doc"
                }
                "ppt" ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_teal), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "ppt"
                }
                "text"->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_grey), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "txt"
                }
                else ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_red), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "other"
                }
            }
            itemView.tb_doc_picker_item_doc_filter_file_ext.background = mDrawable
        }
    }


    interface OnDocClickListener {
        fun onDocCheck(pData: DocTypeModel)
    }
}