package com.uas.halolaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener{
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

            registerUser(email,psw)
        }

        btnSignIn.setOnClickListener{
            Intent(this@Register, Sign::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun registerUser(email: String, psw: String) {
        auth.createUserWithEmailAndPassword(email,psw)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Intent(this@Register,MainActivity::class.java).also{
                        //supaya mengharuskan keluar dengan tombol logout
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}