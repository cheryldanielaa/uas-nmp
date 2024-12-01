package com.rildev.projectuas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import com.rildev.projectuas.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish() //difinish jg biar klo dia ke sign in nda bisa keback
        }
        binding.btnSubmit.setOnClickListener {
            val nama = binding.txtFirstName.text.toString()
            Toast.makeText(this, nama, Toast.LENGTH_SHORT).show()
        }
    }

}