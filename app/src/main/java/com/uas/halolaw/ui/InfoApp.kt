package com.uas.halolaw.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uas.halolaw.R
import kotlinx.android.synthetic.main.activity_info_app.*
import kotlinx.android.synthetic.main.activity_landing_page.*

class InfoApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_app)

        btnLawbot.setOnClickListener{
            Intent(this@InfoApp, MainLawbot::class.java).also {
                startActivity(it)
            }
        }
    }
}