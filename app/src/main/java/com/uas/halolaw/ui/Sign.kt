package com.uas.halolaw.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uas.halolaw.R
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.activity_sign.btnRegister
import kotlinx.android.synthetic.main.activity_sign.btnSignIn

class Sign : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        auth = FirebaseAuth.getInstance()

        btnSignIn.setOnClickListener{
            val email = etEmail.text.toString().trim()
            val psw = etPsw.text.toString().trim()
            //validasi apakah value masih kosong
            if(email.isEmpty()){
                etEmail.error = "Please fill yours email"
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.error = "Email not valid"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (psw.isEmpty() || psw.length<6){
                etEmail.error = "Please fill yours password or > 6 char"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            signInUser(email,psw)

        }

        btnRegister.setOnClickListener{
            Intent(this@Sign, Register::class.java).also {
                startActivity(it)
            }
        }

        //reset psw
        btnForgotPSW.setOnClickListener{
            Intent(this@Sign, ResetPasswordActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun signInUser(email: String, psw: String) {
        auth.signInWithEmailAndPassword(email,psw)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Intent(this@Sign, MainActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        //pengecekan jika user sudah login akan di tujukan
        //lansung ke main menu di dalam APP
        if (auth.currentUser != null){
            Intent(this@Sign, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}