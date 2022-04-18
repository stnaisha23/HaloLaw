package com.uas.halolaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        btnSignInLanding.setOnClickListener{
            Intent(this@LandingPage, Sign::class.java).also {
                startActivity(it)
            }
        }

        btnRegisterLanding.setOnClickListener{
            Intent(this@LandingPage, Register::class.java).also {
                startActivity(it)
            }
        }


    }
}