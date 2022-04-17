package com.uas.halolaw

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_update_email.*
import kotlinx.android.synthetic.main.fragment_update_email.etEmail

class UpdateEmailFragment : Fragment() {

    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= FirebaseAuth.getInstance()

        val user = auth.currentUser

        layoutPassword.visibility = View.VISIBLE
        layoutEmail.visibility = View.GONE

        btnAuth.setOnClickListener{
            val password = etPassword.text.toString().trim()
            if (password.isEmpty()){
                etPassword.error = "Password Harus di isi"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            user?.let {
                val userCredential = EmailAuthProvider.getCredential(it.email!!, password)
                it.reauthenticate(userCredential).addOnCompleteListener{
                    if (it.isSuccessful){
                    layoutPassword.visibility = View.GONE
                    layoutEmail.visibility = View.VISIBLE
                }else if (it.exception is FirebaseAuthInvalidCredentialsException){
                    etPassword.error = "Password Salah"
                        etPassword.requestFocus()
                    }else{
                        Toast.makeText(activity, "${it.exception?.message}",Toast.LENGTH_LONG).show()
                    }
            }
            }
            //btn update email
            btnUpdate.setOnClickListener{

                val email = etEmail.text.toString().trim()

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

                user?.let {
                    user.updateEmail(email)
                    Toast.makeText(activity, "Email di perbarui, masuk ulang", Toast.LENGTH_LONG).show()

                    auth.signOut()
                    val myIntent = Intent(activity, Sign::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    }

                }

        }
    }

}