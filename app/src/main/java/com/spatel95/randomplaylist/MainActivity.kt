package com.spatel95.randomplaylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor


class MainActivity : Activity(), AnkoLogger {

    companion object {
        const val LOGIN_REQUEST_CODE = 1
    }

    var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // go to login activity
        startActivityForResult(intentFor<LoginActivity>(), LOGIN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            LOGIN_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        accessToken = data.getStringExtra(LoginActivity.ACCESS_TOKEN)
                        info("token: " + accessToken)
                        FuelManager.instance.baseHeaders = mapOf("Authorization" to "Bearer " + accessToken)
                        getSavedSongs()
                    } else {
                        error("Error setting access token")
                    }
                }
            }
        }
    }

    // get user's saved songs
    private fun getSavedSongs() {
        "https://api.spotify.com/v1/me/tracks".httpGet().responseString { request, response, result ->
            info(request.toString())
            when (result) {
                is Result.Failure -> {
                    error("error: " + result.toString())
                }
                is Result.Success -> {
                    info(result.get())
                    // TODO: Use result
                }
            }
        }
    }
}
