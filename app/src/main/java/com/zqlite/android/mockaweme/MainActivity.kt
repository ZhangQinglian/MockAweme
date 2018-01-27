package com.zqlite.android.mockaweme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val mainFragment = MainFragment.getInstance(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, mainFragment).commit()
    }

    override fun onBackPressed() {
        if(mainFragment.onBackPress()){
            return
        }
        super.onBackPressed()
    }
}
