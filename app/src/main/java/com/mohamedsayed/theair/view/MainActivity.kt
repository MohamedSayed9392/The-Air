package com.mohamedsayed.theair.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohamedsayed.theair.R

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}