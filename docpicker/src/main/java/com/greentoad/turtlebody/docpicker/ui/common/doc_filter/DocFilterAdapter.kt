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
import com.greentoad.turtlebody.docpicker.core.DocConstants
import kotlinx.android.synthetic.main.tb_doc_picker_item_doc_filter.view.*
import org.jetbrains.anko.AnkoLogger


/**
 * Created by WANGSUN on 26-Mar-19.
 */
class DocFilterAdapter: RecyclerView.Adapter<DocFilterAdapter.DocVewHolder>(), AnkoLogger {
    private var mData: MutableList<DocFilterModel> = arrayListOf()
    private var mOnDocFilterClickListener: OnDocFilterClickListener? = null
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


    fun setListener(listenerFilter : OnDocFilterClickListener){
        mOnDocFilterClickListener = listenerFilter
    }

    fun setData(pData: MutableList<DocFilterModel>){
        mData = pData
        notifyDataSetChanged()
    }

    fun updateIsSelected(pData: DocFilterModel){
        val pos = mData.indexOf(pData)
        if(pos>=0){
            mData[pos] = pData
            notifyItemChanged(pos)
        }
    }

    inner class DocVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: DocFilterModel){

            setDrawableForMime(itemView, pData.docType)

            itemView.tb_doc_picker_item_doc_filter_ivc.isChecked = pData.isSelected

            itemView.tb_doc_picker_doc_filter_doc_type.text = pData.docType

            itemView.setOnClickListener {
                mOnDocFilterClickListener?.onDocCheck(pData)
            }
        }

        private fun setDrawableForMime(itemView: View, fileType: String) {

            val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.dr_rect_round_red_doc_background)

            when(fileType.toLowerCase()){
                DocConstants.DocTypes.PDF-> {
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_red), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "pdf"
                }
                DocConstants.DocTypes.MS_WORD->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_dark_blue), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "doc"
                }
                DocConstants.DocTypes.MS_POWERPOINT ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_teal), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "ppt"
                }
                DocConstants.DocTypes.MS_EXCEL ->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_picker_color_orange), PorterDuff.Mode.SRC)
                    itemView.tb_doc_picker_item_doc_filter_file_ext.text = "xls"
                }
                DocConstants.DocTypes.TEXT->{
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


    interface OnDocFilterClickListener {
        fun onDocCheck(pData: DocFilterModel)
    }
}