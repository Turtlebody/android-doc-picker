package com.greentoad.turtlebody.docpicker.ui.common.bottom_sheet_filter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.greentoad.turtlebody.docpicker.R
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.greentoad.turtlebody.docpicker.core.DocConstants
import kotlinx.android.synthetic.main.tb_doc_picker_selected_doc_layout.view.*
import org.jetbrains.anko.textColor


/**
 * Created by WANGSUN on 26-Apr-19.
 */
class SelectedDocsLayout(private val mParentView: LinearLayout, private val mSelectedFilters: ArrayList<String>) {

    private val context: Context = mParentView.context
    private var mLayout: View = LayoutInflater.from(context).inflate(R.layout.tb_doc_picker_selected_doc_layout,  null)

    fun updateSelectedViews(){

        //always do a fresh start
        mLayout.tb_doc_picker_selected_doc_layout_txt_0.visibility = View.GONE
        mLayout.tb_doc_picker_selected_doc_layout_txt_1.visibility = View.GONE
        mLayout.tb_doc_picker_selected_doc_layout_txt_2.visibility = View.GONE


        for(i in mSelectedFilters.indices){
            updateViews(i, mSelectedFilters[i])
        }

        mParentView.removeAllViews()
        mParentView.addView(mLayout)
    }

    private fun updateViews(count: Int, docType: String) {
        when(count){
            0->updateColor(mLayout.tb_doc_picker_selected_doc_layout_txt_0,docType)
            1->updateColor(mLayout.tb_doc_picker_selected_doc_layout_txt_1,docType)
            2->updateColor(mLayout.tb_doc_picker_selected_doc_layout_txt_2,docType)
            else->{
                updateColor(mLayout.tb_doc_picker_selected_doc_layout_txt_2,"${count-1}+")
            }
        }
    }

    private fun updateColor(view: TextView, docType: String) {
        val mDrawable = ContextCompat.getDrawable(context, R.drawable.dr_rect_round_red_doc_background)

        when(docType){
            DocConstants.DocTypes.PDF-> {
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.tb_doc_picker_color_light_red), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.tb_doc_picker_color_red)
                view.text = DocConstants.docTypeMaps()[DocConstants.DocTypes.PDF]
                view.visibility = View.VISIBLE
            }
            DocConstants.DocTypes.MS_WORD->{
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.tb_doc_picker_color_light_blue), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.tb_doc_picker_color_blue)
                view.text = DocConstants.docTypeMaps()[DocConstants.DocTypes.MS_WORD]
                view.visibility = View.VISIBLE
            }
            DocConstants.DocTypes.MS_POWERPOINT ->{
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.tb_doc_picker_color_light_teal), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.tb_doc_picker_color_teal)
                view.text = DocConstants.docTypeMaps()[DocConstants.DocTypes.MS_POWERPOINT]
                view.visibility = View.VISIBLE
            }
            DocConstants.DocTypes.MS_EXCEL ->{
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.tb_doc_picker_color_light_orange), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.tb_doc_picker_color_orange)
                view.text = DocConstants.docTypeMaps()[DocConstants.DocTypes.MS_EXCEL]
                view.visibility = View.VISIBLE
            }
            DocConstants.DocTypes.TEXT->{
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.tb_doc_picker_color_light_grey), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.md_black_1000)
                view.text = DocConstants.docTypeMaps()[DocConstants.DocTypes.TEXT]
                view.visibility = View.VISIBLE
            }
            else ->{
                mDrawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context,R.color.md_grey_300), PorterDuff.Mode.SRC)
                view.textColor = ContextCompat.getColor(context,R.color.md_black_1000)
                view.text = docType
                view.visibility = View.VISIBLE
            }
        }
        view.background=mDrawable
    }

}