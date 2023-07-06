package com.hbl.assetsmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragmentDemo = HawPdfViewUploadFragment44()
        getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        //above part is to determine which fragment is in your frame_container
        setFragment(fragmentDemo);
    }
     private fun setFragment(fragment: Fragment?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(android.R.id.content, fragment!!)
        fragmentTransaction.commit()
    }
    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances

}