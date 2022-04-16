package com.uas.halolaw

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profileuser.*
import kotlinx.android.synthetic.main.activity_register.*

class Profileuser : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileuser)

        auth = FirebaseAuth.getInstance()

        btnLogOut.setOnClickListener{
            auth.signOut()
            //flags berfunsgi untuk tidak bisa kembali ke halaman
            //sebelumnya jika menekan log out
            Intent(this@Profileuser, LandingPage::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        /*btnLawbot.setOnClickListener{
            Intent(this@Profileuser, Lawbot::class.java).also {
                startActivity(it)
            }
        }*/

        btnInfoApp.setOnClickListener{
            Intent(this@Profileuser, InfoApp::class.java).also {
                startActivity(it)
            }
        }
        btnEdit.setOnClickListener{
            Intent(this@Profileuser, fragment::class.java).also {
                startActivity(it)
            }
        }
    }


}