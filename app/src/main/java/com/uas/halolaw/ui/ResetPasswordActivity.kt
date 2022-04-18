package com.uas.halolaw.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uas.halolaw.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btnReset.setOnClickListener{
            val email = Emailbaru.text.toString().trim()
            //validasi apakah value masih kosong
            if(email.isEmpty()){
                Emailbaru.error = "Isikan email"
                Emailbaru.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Emailbaru.error = "Email tidak valid"
                Emailbaru.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this, "Kode verifikasi telah dikirim", Toast.LENGTH_LONG).show()
                    Intent(this@ResetPasswordActivity, Sign::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}