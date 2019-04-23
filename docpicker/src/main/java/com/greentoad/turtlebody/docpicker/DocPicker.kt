package com.greentoad.turtlebody.docpicker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.greentoad.turtlebody.docpicker.core.Constants
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig
import com.greentoad.turtlebody.docpicker.ui.components.ActivityLibMain
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.ref.WeakReference


/**
 * Created by WANGSUN on 29-Mar-19.
 */
class DocPicker {

    companion object {
        @JvmStatic
        fun with(activity: FragmentActivity): DocPickerImpl {
            return DocPickerImpl(activity)
        }
    }

    class DocPickerImpl(activity: FragmentActivity) : PickerFragment.OnPickerListener, AnkoLogger {
        private lateinit var mEmitter: ObservableEmitter<ArrayList<Uri>>
        private var mActivity: WeakReference<FragmentActivity> = WeakReference(activity)
        private var mConfigDoc: DocPickerConfig = DocPickerConfig()
        private var mOnMediaListener: OnMediaListener? = null


        override fun onData(data: ArrayList<Uri>) {
            mEmitter.onNext(data)
            mEmitter.onComplete()
        }

        override fun onCancel(message: String) {
            mEmitter.onError(Throwable(message));
            mEmitter.onComplete()
        }

        override fun onMissingWarning() {
            mActivity.get()?.let {
                it.runOnUiThread {
                    mOnMediaListener?.onMissingFileWarning()
                }
            }
        }


        /**
         * set configuration
         * @param configDoc pass DocPickerConfig
         */
        fun setConfig(configDoc: DocPickerConfig): DocPickerImpl{
            mConfigDoc = configDoc
            return this
        }

        /**
         * Register a callback to be invoked when missing files are filtered out.
         * @param listener The callback that will run
         */
        fun setFileMissingListener(listener: OnMediaListener): DocPickerImpl{
            mOnMediaListener = listener
            return this
        }

        /**
         * @return observable uri list
         */
        fun onResult(): Observable<ArrayList<Uri>> {
            return Observable.create<ArrayList<Uri>> { emitter: ObservableEmitter<ArrayList<Uri>> ->
                this.mEmitter = emitter
                getPermission()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * run runtime-permission to read external file
         */
        @SuppressLint("CheckResult")
        private fun getPermission() {
            mActivity.get()?.let {
                it.runOnUiThread {
                    Dexter.withActivity(it)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(object : PermissionListener {
                                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                                    startFragment()
                                    info { "accepted" }
                                }
                                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                                    Toast.makeText(it, "Need permission to do this task.", Toast.LENGTH_SHORT).show()
                                    info { "denied" }
                                }
                                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                                    token.continuePermissionRequest()
                                    info { "rational" }
                                }
                            }).check()
                }
            }
        }


        private fun startFragment() {
            val bundle = Bundle()
            bundle.putSerializable(DocPickerConfig.ARG_BUNDLE, mConfigDoc)

            val fragment = PickerFragment()
            fragment.arguments = bundle
            fragment.setListener(this)
            mActivity.get()?.supportFragmentManager?.beginTransaction()?.add(fragment, PickerFragment::class.java.simpleName)?.commit()
        }

        interface OnMediaListener {
            fun onMissingFileWarning()
        }
    }


    /**************************************************
     *              Fragment
     */
    class PickerFragment : Fragment(), AnkoLogger {
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            val config = arguments?.getSerializable(DocPickerConfig.ARG_BUNDLE)

            val intent = Intent(context, ActivityLibMain::class.java)
            intent.putExtra(DocPickerConfig.ARG_BUNDLE, config)
            startActivityForResult(intent, Constants.Intent.ACTIVITY_LIB_MAIN)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == Constants.Intent.ACTIVITY_LIB_MAIN) {
                if (resultCode == Activity.RESULT_OK) {
                    val list = data?.extras?.getSerializable(ActivityLibMain.B_ARG_URI_LIST) as ArrayList<Uri>
                    mListener?.onData(list)
                    data.extras?.getBoolean(ActivityLibMain.B_ARG_FILE_MISSING,false)?.let {
                        if(it){
                            mListener?.onMissingWarning()
                        }
                    }
                }
                else
                    mListener?.onCancel("Cancelled")
            } else
                super.onActivityResult(requestCode, resultCode, data)
        }

        private var mListener: OnPickerListener? = null

        fun setListener(listener: OnPickerListener) {
            this.mListener = listener
        }

        interface OnPickerListener {
            fun onData(data: ArrayList<Uri>)
            fun onCancel(message: String)
            fun onMissingWarning()
        }
    }
}