package com.rildev.projectuas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
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
        var listUser = UserData.users

        //simpan login state disini?
        var login_state = false //basicnya false

        //buat sharedpreferencesnya disini
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        login_state = sharedPreferences.getBoolean("LOGIN_STATE",
            false) //basicnya false

        //cek apakah bisa login atau nggak
        binding.btnSubmit.setOnClickListener {
            var statusLogin=false
            //baca inputan username dan password user
            var usn = binding.txtUsername.text.toString()
            var pass =binding.txtPassword.text.toString()
            for (user in listUser) {
                if (user.username == usn && user.password == pass) {
                    statusLogin=true
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    break
                }
            }
            if(statusLogin==true){
                //klo true maka update shared preferencesnya
                login_state=true //login statenya jadi true (buat simpen status loginnya)
                val editor = sharedPreferences.edit()
                editor.putBoolean("LOGIN_STATE", login_state)
                editor.apply()

                //klo benar maka muncul main activity sama bikin user shared preferences
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() //biar gk bisa back lagi
            }
            else{
                Toast.makeText(this, "Login gagal. Username/Password yang Anda masukkan salah!",
                    Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish() //biar gk bs ngeback
        }

        //klo login statenya true, maka dia langsung ke main menu
        if(login_state==true)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() //biar gk bs ngeback
        }
    }
}