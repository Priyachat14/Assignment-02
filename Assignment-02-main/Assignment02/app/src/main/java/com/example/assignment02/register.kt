package com.example.assignment02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.reflect.Member

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var auth: FirebaseAuth? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            val it = Intent(this, member::class.java)
            startActivity(it)
            finish()
        }
        bok.setOnClickListener {
            val email = eemail.text.toString().trim()
            val pass = epass.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกPassword", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (pass.length < 8) {
                        epass.error = "กรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร"
                    } else {
                        Toast.makeText(
                            this,
                            " Login ล้มเหลว เนื่องจาก : " + task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    eemail.setText("")
                    epass.setText("")
                } else {
                    Toast.makeText(this, " Login Success! ", Toast.LENGTH_LONG).show()
                    val it = Intent(this, member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }
        blogin.setOnClickListener {
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
        }
    }
}