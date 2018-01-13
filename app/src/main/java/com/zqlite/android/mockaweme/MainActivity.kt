package com.zqlite.android.mockaweme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zqlite.android.mockaweme.fragment.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container,HomeFragment.getInstance(null)).commit()
    }
}
