package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.rildev.projectuas.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //baca list user
        val listUser: ArrayList<User> = UserData.users

        //baca inputan username dan password user
        var usn = binding.txtUsername.text
        var pass =binding.txtPassword.text
    }
}