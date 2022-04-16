package com.uas.halolaw

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var imageUri : Uri
    private val REQUEST_IMAGE_CAPTURE = 100
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user : FirebaseUser? = auth.currentUser

        //pengecekan jika user login maka akan idtmapilkan foto sesuai url tersimpan != maka random dari url
        // into = akan memasukkan ke dalam image veiw/id
        if(user != null){
            if(user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(profileimage)
            }else{
                Picasso.get().load("https://picsum.photos/200/300").into(profileimage)
            }

            fragName.setText(user.displayName)
            fragEmail.setText(user.email)
            NameUser.setText(user.displayName)

            //cek verified email
            if(user.isEmailVerified){
                icverified.visibility = View.VISIBLE
            }else{
                icUnverified.visibility = View.VISIBLE
            }

        }

        profileimage.setOnClickListener{
            takePictureIntent()
        }

        //button info App
        btnInfoApp.setOnClickListener{
            val myIntent = Intent(activity, InfoApp::class.java)
            startActivity(myIntent)
        }

        //btn edit profile
        btnEdit.setOnClickListener{
            btnUpdate.visibility = View.VISIBLE
            fragName.requestFocus()
        }

        //btn logout
        btnLogOut.setOnClickListener{
            auth.signOut()
            val myIntent = Intent(activity, LandingPage::class.java)
            startActivity(myIntent)
        }

        //save button/update
        btnUpdate.setOnClickListener{

            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse("https://picsum.photos/200/300")
                else-> user.photoUrl
        }
            val name = fragName.text.toString().trim()
            if(name.isEmpty()){
                fragName.error = "Please fill name"
                return@setOnClickListener
            }
            //request perubahan dan upload ke firebase
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(activity, "Profile di perbarui", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            btnUpdate.visibility = View.INVISIBLE
            fragName.clearFocus()
        }
        icUnverified.setOnClickListener{
            user?.sendEmailVerification()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(activity,"Verified Email send!", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity,"Verified Email Failed!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            pictureIntent->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
                Log.d("p","di ambil")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap

            uploadImageAndSaveUri(imageBitmap)
        }else{
            Log.d("p","gagal")
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance().reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)

        progressBar.visibility=View.VISIBLE
        upload.addOnCompleteListener{
            uploadTask ->
            progressBar.visibility=View.INVISIBLE
            if (uploadTask.isSuccessful){
                storageRef.downloadUrl.addOnCompleteListener{urlTask->
                    urlTask.result?.let{
                        imageUri = it
                        profileimage.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

}