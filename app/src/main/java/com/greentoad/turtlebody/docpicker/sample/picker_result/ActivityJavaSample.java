package com.greentoad.turtlebody.docpicker.sample.picker_result;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.greentoad.turtlebody.docpicker.DocPicker;
import com.greentoad.turtlebody.docpicker.core.DocPickerConfig;
import com.greentoad.turtlebody.docpicker.sample.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;

public class ActivityJavaSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<String> docs = new ArrayList<String>();
        docs.add(DocPicker.DocTypes.PDF);
        docs.add(DocPicker.DocTypes.MS_POWERPOINT);
        docs.add(DocPicker.DocTypes.MS_EXCEL);
        docs.add(DocPicker.DocTypes.TEXT);


        DocPickerConfig pickerConfig = new DocPickerConfig()
                .setAllowMultiSelection(false)
                .setShowConfirmationDialog(true)
                .setExtArgs(docs);

        DocPicker.with(this)
                .setConfig(pickerConfig)
                .onResult()
                .subscribe(new Observer<ArrayList<Uri>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(ArrayList<Uri> uris) {
                        //uris: list of uri
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { }
                });



    }
}
