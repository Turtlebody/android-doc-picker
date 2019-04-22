package com.greentoad.turtlebody.docpicker.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.greentoad.turtlebody.docpicker.R
import com.greentoad.turtlebody.docpicker.core.FileManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info



class ActivityLibMain : AppCompatActivity(),AnkoLogger {

    companion object{
        const val B_ARG_FILE_MISSING = "activity.lib.main.file.missing"
        const val B_ARG_FILE_TYPE = "activity.lib.main.file.type"
        const val B_ARG_URI_LIST = "activity.lib.main.uri.list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lib_main)


        getPermission()
    }

    @SuppressLint("CheckResult")
    private fun getPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    fetchAudioFolders()
                    //fetchAudioFiles()
                    info { "accepted" }
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(this@ActivityLibMain, "Need permission to do this task.", Toast.LENGTH_SHORT).show()
                    info { "denied" }
                }
                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                    info { "rational" }
                }
            }).check()
    }

    private fun fetchAudioFolders() {
        val bucketFetch = Single.fromCallable<ArrayList<DocFolder>> {
            FileManager.fetchAudioFolderList(this) }
        bucketFetch
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<DocFolder>> {
                override fun onSubscribe(@NonNull d: Disposable) {
                }

                override fun onSuccess(@NonNull audioFolders: ArrayList<DocFolder>) {
                    for(i in audioFolders){
                        info { "folders: $i\n" }
                    }

                    info { "size: ${audioFolders.size}" }
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    info { "error: ${e.message}" }
                }
            })
    }

    private fun fetchAudioFiles() {
        val fileItems = Single.fromCallable<Boolean> {
           // mAudioModelList.clear()
            val tempArray = FileManager.getDocFilesInFolder(this)

            info { "List: $tempArray" }
            info { "size: ${tempArray.size}" }

//            //include only valid files
//            for(i in tempArray){
//                if(File(i.filePath).length()>0){
//                    mAudioModelList.add(i)
//                }
//            }
            true
        }

        fileItems.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(@NonNull d: Disposable) {
                   // progress_view.visibility = View.VISIBLE
                }

                override fun onSuccess(t: Boolean) {
                   // mAudioAdapter.setData(mAudioModelList)
                   // progress_view.visibility = View.GONE
                }

                override fun onError(@NonNull e: Throwable) {
                   // progress_view.visibility = View.GONE
                    info { "error: ${e.message}" }
                }
            })
    }
}
