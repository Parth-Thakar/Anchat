package com.parth.anchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {


    private lateinit var edtEmail: EditText
    private lateinit var edtpass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtpass = findViewById(R.id.edt_pass)
        btnLogin = findViewById(R.id.btlog_in)
        btnSignup = findViewById(R.id.btSign_up)


        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtpass.text.toString()

            login(email, pass)
        }


        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

    }


    private fun login(email: String, password: String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home activity
                    val intent = Intent(this,  MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error in Credential bhai sahab: ", Toast.LENGTH_SHORT).show()
                }
            }
    }



}