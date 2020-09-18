package com.mohamedsayed.theair.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mohamedsayed.theair.R
import com.nahdetmisr.adwaa.view.dashboard.fragments.LatestFragment

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(LatestFragment.newInstance())
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.addToBackStack(fragment.javaClass.canonicalName)
        transaction.replace(R.id.frameLayout, fragment, fragment.javaClass.canonicalName)
        transaction.commit()
    }
}