package com.spatel95.randomplaylist

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor


class MainActivity : Activity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info("main activity")

        // go to login activity
        startActivity(intentFor<LoginActivity>())
    }
}
