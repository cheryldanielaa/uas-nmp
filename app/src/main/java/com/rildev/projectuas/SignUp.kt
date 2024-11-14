package com.rildev.projectuas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import com.rildev.projectuas.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inisialisasi state button di awal
        binding.checkBox.isChecked = false
        binding.btnSubmit.isEnabled = false

        //tiap kali checkbox di check, button submit enabled disabled
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSubmit.isEnabled = isChecked
        }

        var listUser = UserData.users

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish() //difinish jg biar klo dia ke sign in nda bisa keback
        }

        binding.btnSubmit.setOnClickListener {
            //trim() buat hapus spasi di awal & akhir ga terdeteksi empty
            val firstName = binding.txtFirstName.text.toString().trim()
            val lastName = binding.txtLastName.text.toString().trim()
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            val repeatPassword = binding.txtRepeatPassword.text.toString().trim()

            for (user in listUser) {
                //cek apakah semua input nya kosong atau ga
                if (firstName != "" && lastName != "" && username != "" && password != "") {
                    //cek apakah username udah terdaftar atau belum
                    if (user.username != username) {
                        val newUser = User(firstName, lastName, username, password)

                        //ubah tipe array UserData dari array ke MutableList
                        //kenapa diubah? karena kalo array itu kita gabisa nambah data lewat code -> tapi kalo pake MutableList bisa
                        val userList = UserData.users.toMutableList()
                        userList.add(newUser)
                        //ubah list ke ArrayList
                        val arrayList: ArrayList<User> = ArrayList(userList)
                        UserData.users = arrayList //kalo udah di add, ubah balik jadi arrayList

                        Toast.makeText(this, "Sign Up berhasil", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, SignIn::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Tambahkan TextWatcher untuk validasi password
        binding.txtRepeatPassword.addTextChangedListener(object : TextWatcher {
            //afterTextChanged ini buat deteksi txtRepeatPassword setiap user ngetik
            override fun afterTextChanged(s: Editable?) {
                val pass = binding.txtPassword.text.toString()
                val repPass = binding.txtRepeatPassword.text.toString()

                if (pass != repPass) {
                    binding.repeatPasswordInputLayout.error = "Password not match"
                } else {
                    binding.repeatPasswordInputLayout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}