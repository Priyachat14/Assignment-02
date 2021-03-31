package com.example.assignment02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Member

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var auth : FirebaseAuth? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser !=null){
            val it = Intent(this, member::class.java)
            startActivity(it)
            finish()
        }

        bSign.setOnClickListener {
            val email = memail.text.toString().trim()
            val pass = mpass.text.toString().trim()

            if (email.isEmpty()){

                Toast.makeText(this,"กรุณากรอกEmail", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                Toast.makeText(this,"กรุณากรอกPassword", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if(pass.length<8){
                        mpass.error="กรอกรหัสผ่านให้มากกว่า8ตัวอักษร"
                    }else{
                        Toast.makeText(this,"Login ล้มเหลว เนื่องจาก:"+task.exception!!.message,
                            Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this,"Login Success!", Toast.LENGTH_LONG).show()
                    val  it =Intent(this,member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }

        bRegister.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }
    }
}