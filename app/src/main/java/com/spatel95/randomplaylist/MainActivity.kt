package com.spatel95.randomplaylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // go to login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
