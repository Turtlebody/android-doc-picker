package com.greentoad.turtlebody.docpicker.ui.common.bottom_sheet_filter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.greentoad.turtlebody.docpicker.DocPicker
import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.DocConstants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import kotlinx.android.synthetic.main.tb_doc_picker_selected_doc_layout.view.*
import org.jetbrains.anko.textColor


/**
 * Created by WANGSUN on 26-Apr-19.
 */
class SelectedDocsLayout(private val mParentView: LinearLayout, private val mSelectedFilters: ArrayList<String>,private  var mPickerConfig: DocPickerConfig) {

    private val mContext: Context = mParentView.context
    private var mLayout: View = LayoutInflater.from(mContext).inflate(R.layout.tb_doc_picker_selected_doc_layout,  null)

    fun updateSelectedViews(){

        //always do a fresh start
        mLayout.selected_doc_view_txt_0.visibility = View.GONE
        mLayout.selected_doc_view_txt_1.visibility = View.GONE
        mLayout.selected_doc_view_txt_2.visibility = View.GONE


        for(i in mSelectedFilters.indices){
            updateViews(i, mSelectedFilters[i])
        }

        mParentView.removeAllViews()
        mParentView.addView(mLayout)
    }

    private fun updateViews(count: Int, docType: String) {
        when(count){
            0->updateColor(mLayout.selected_doc_view_txt_0,docType)
            1->updateColor(mLayout.selected_doc_view_txt_1,docType)
            2->updateColor(mLayout.selected_doc_view_txt_2,docType)
            else->{
                updateColor(mLayout.selected_doc_view_txt_2,"${count-1}+")
            }
        }
    }

    private fun updateColor(view: TextView, docType: String) {
        val mDrawable = ContextCompat.getDrawable(mContext, R.drawable.tb_doc_picker_dr_rect_round_red_doc_background)

        when {
            docType.contains("+") -> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.md_grey_300), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(mContext,R.color.md_black_1000)
                view.visibility = View.VISIBLE
                view.text = docType
            }
            docType == DocPicker.DocTypes.AUDIO -> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_light_audio), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(mContext,R.color.tb_doc_audio)
                view.visibility = View.VISIBLE
                view.text = DocConstants.docTypeMapLabel()[docType]

            }
            docType == DocPicker.DocTypes.IMAGE -> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_light_image), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(mContext,R.color.tb_doc_image)
                view.visibility = View.VISIBLE
                view.text = DocConstants.docTypeMapLabel()[docType]

            }
            docType == DocPicker.DocTypes.VIDEO -> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,R.color.tb_doc_light_video), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(mContext,R.color.tb_doc_video)
                view.visibility = View.VISIBLE
                view.text = DocConstants.docTypeMapLabel()[docType]

            }
            else -> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext,mPickerConfig.mDocLabelSet.getLabelForExt(DocConstants.docTypeMapLabel()[docType]!!).colorLightRes), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(mContext,mPickerConfig.mDocLabelSet.getLabelForExt(DocConstants.docTypeMapLabel()[docType]!!).colorRes)
                view.visibility = View.VISIBLE
                view.text = DocConstants.docTypeMapLabel()[docType]
            }
        }

        view.background=mDrawable
    }

}