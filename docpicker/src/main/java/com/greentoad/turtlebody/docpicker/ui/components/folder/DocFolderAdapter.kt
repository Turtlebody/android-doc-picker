package com.greentoad.turtlebody.docpicker.ui.components.folder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greentoad.turtlebody.docpicker.R
import kotlinx.android.synthetic.main.tb_doc_picker_item_folder.view.*

/**
 * Created by WANGSUN on 26-Mar-19.
 */
class DocFolderAdapter: RecyclerView.Adapter<DocFolderAdapter.FolderVewHolder>() {
    private var mData: MutableList<DocFolder> = arrayListOf()
    private var mOnDocFolderClickListener: OnDocFolderClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderVewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tb_doc_picker_item_folder, parent, false)
        return FolderVewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: FolderVewHolder, position: Int) {
        holder.bind(mData[position])
    }


    /**
     * Register a callback to be invoked when folder mParentView is clicked.
     * @param listener The callback that will run
     */
    fun setListener(listenerAudio : OnDocFolderClickListener){
        mOnDocFolderClickListener = listenerAudio
    }

    /**
     * @param pData mutable-list-of DocFolder
     */
    fun setData(pData: MutableList<DocFolder>){
        mData = pData
        notifyDataSetChanged()
    }

    inner class FolderVewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(pData: DocFolder){

            itemView.item_folder_folder_name.text = pData.name
            itemView.item_folder_total_items.text = "${pData.contentCount} items"

            itemView.setOnClickListener {
                mOnDocFolderClickListener?.onFolderClick(pData)
            }
        }
    }


    interface OnDocFolderClickListener {
        fun onFolderClick(pData: DocFolder)
    }
}