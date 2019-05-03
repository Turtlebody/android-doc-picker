package com.greentoad.turtlebody.docpicker.ui.common.bottom_sheet_filter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.DocConstants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import kotlinx.android.synthetic.main.tb_doc_picker_item_doc_filter.view.*
import org.jetbrains.anko.AnkoLogger


/**
 * Created by WANGSUN on 26-Mar-19.
 */
class DocFilterAdapter: RecyclerView.Adapter<DocFilterAdapter.DocVewHolder>(), AnkoLogger {
    private var mData: MutableList<DocFilterModel> = arrayListOf()
    private var mOnDocFilterClickListener: OnDocFilterClickListener? = null
    private var mPickerConfig: DocPickerConfig = DocPickerConfig()
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

    fun setPickerConfig(config: DocPickerConfig){
        mPickerConfig = config
    }

    inner class DocVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: DocFilterModel){

            setDrawableForMime(itemView, pData.docType)

            itemView.item_doc_filter_ivc.isChecked = pData.isSelected

            itemView.item_doc_filter_doc_type.text = pData.docType

            itemView.setOnClickListener {
                mOnDocFilterClickListener?.onDocClick(pData)
            }
        }

        private fun setDrawableForMime(itemView: View, docType: String) {

            val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.tb_doc_picker_dr_rect_round_red_doc_background)


            when(docType){
                DocPicker.DocTypes.IMAGE->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_image), PorterDuff.Mode.SRC)
                    itemView.item_doc_filter_file_ext.text = DocConstants.docTypeMapLabel()[docType]
                }
                DocPicker.DocTypes.AUDIO->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_audio), PorterDuff.Mode.SRC)
                    itemView.item_doc_filter_file_ext.text = DocConstants.docTypeMapLabel()[docType]
                }
                DocPicker.DocTypes.VIDEO->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_video), PorterDuff.Mode.SRC)
                    itemView.item_doc_filter_file_ext.text = DocConstants.docTypeMapLabel()[docType]

                }
                else->{
                    mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,mPickerConfig.mDocLabelSet.getLabelForExt(DocConstants.docTypeMapLabel()[docType]!!).colorRes), PorterDuff.Mode.SRC)
                    itemView.item_doc_filter_file_ext.text = DocConstants.docTypeMapLabel()[docType]
                }
            }
            itemView.item_doc_filter_file_ext.background = mDrawable
        }
    }


    interface OnDocFilterClickListener {
        fun onDocClick(pData: DocFilterModel)
    }
}