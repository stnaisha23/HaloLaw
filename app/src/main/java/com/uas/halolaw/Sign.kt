package com.uas.halolaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign.*

class Sign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        btnRegister.setOnClickListener{
            Intent(this@Sign, Register::class.java).also {
                startActivity(it)
            }
        }
    }
}