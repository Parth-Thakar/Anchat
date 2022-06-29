package com.parth.anchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {


    private lateinit var edtname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtpass: EditText

    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtpass = findViewById(R.id.edt_pass)

        btnSignup = findViewById(R.id.btSign_up)
        edtname = findViewById(R.id.edt_name)


        btnSignup.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtpass.text.toString()
            val name = edtname.text.toString()

            signup(email,pass,name)
        }

    }


    private fun signup(email: String, pass: String,name: String)
    {
        // logic of signup
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home activity

                        addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this,  MainActivity::class.java)
                        finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "There is Some Error Bhai Shab ",Toast.LENGTH_SHORT).show()

                }
            }




    }

        private fun addUserToDatabase(name: String,email: String, uid: String)
        {
            mDbRef = FirebaseDatabase.getInstance().getReference()

            mDbRef.child("user").child(uid).setValue(User(name, email, uid))

        }

}