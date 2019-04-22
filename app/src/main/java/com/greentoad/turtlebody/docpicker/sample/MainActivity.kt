package com.greentoad.turtlebody.docpicker.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greentoad.turtlebody.docpicker.ui.ActivityLibMain

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this,ActivityLibMain::class.java))
    }
}
