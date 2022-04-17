package com.uas.halolaw

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intentTitle = intent.getStringExtra("intent_title")
        val intentImage = intent.getStringExtra("intent_image")
        namaPengacara.setText(intentTitle)
        Glide.with(this)
            .load(intentImage )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageView)

        //btn sms
        btnSms.setOnClickListener{
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            startActivity(sendIntent);
        }

        //btn ig
        btnIg.setOnClickListener{
            val uri = Uri.parse("http://www.instagram.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        //btn linked
        btnLinked.setOnClickListener{
            val uri = Uri.parse("https://www.linkedin.com/feed/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        //btn twiter
        btnTw.setOnClickListener{
            val uri = Uri.parse("https://twitter.com/home")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}