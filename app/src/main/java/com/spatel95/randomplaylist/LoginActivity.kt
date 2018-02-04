package com.spatel95.randomplaylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.spatel95.randomplaylist.config.Keys
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : Activity(), AnkoLogger {

    companion object {
        const val REQUEST_CODE = 1
        const val REDIRECT_URI = "random-playlist://callback"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        debug("LOGIN ACTIVITY")
//        debug("login activity")

        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val builder = AuthenticationRequest.Builder(Keys.SPOTIFY_CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf("user-library-read", "playlist-modify-private", "playlist-modify-public", "playlist-read-private"))
        val request = builder.build()
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    private fun startMainActivity() {
        toast("START MAIN ACTIVITY")
        debug("START MAIN ACTIVITY")
        startActivity(intentFor<MainActivity>())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, data)
            debug("RESPONSE")
            when (response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    debug("token: " + response.accessToken)
                    startMainActivity()
                    startActivity(intentFor<MainActivity>())
                    debug("AFTER START MAIN ACTIVITY")
                    toast("login successful")
                }
                AuthenticationResponse.Type.ERROR -> {
                    toast("login error")
                }
                else -> warn("login cancelled")
            }
            debug("AFTER WHEN")
        }
    }
}