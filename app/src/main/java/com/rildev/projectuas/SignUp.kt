package com.rildev.projectuas

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import com.rildev.projectuas.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish() //difinish jg biar klo dia ke sign in nda bisa keback
        }

        binding.txtRepeatPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.txtPassword.text.toString()
                val repeatPassword = binding.txtRepeatPassword.text.toString()

                if (repeatPassword != password) {
                    binding.repeatPasswordInputLayout.error = "Passwords do not match"
                }
                else {
                    binding.repeatPasswordInputLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //klo misal checkbox dipencet, maka btn submit enabled nya nyala
        binding.checkBox.setOnClickListener{
            //status enablednya button submit = status checkednya checkbox
            binding.btnSignUp.isEnabled = binding.checkBox.isChecked
        }

        binding.btnSignUp.setOnClickListener {
            //ambil value dari textbox
            val fname = binding.txtFirstName.text.toString()
            val lname = binding.txtLastName.text.toString()
            val username = binding.txtUsername.text.toString()
            val password=binding.txtPassword.text.toString()
            val repeatPassword = binding.txtRepeatPassword.text.toString()

            //cek apakah data ada yg kosong atau ndak
            if(fname.isNotEmpty() && lname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()){
                //klo semua udh keisi cek apakah udh sama blm pw sm repeatnya
                if(password==repeatPassword) //klo sama jalanin volleynya
                {
                    //urus ke database
                    val q = Volley.newRequestQueue(this) //karena layout sign in itu activity, maka pake this
                    //panggil url dimana apinya dibuat
                    val url = "https://ubaya.xyz/native/160422026/project/signup.php"
                    //kirim data ke db waktu mau signup
                    val stringRequest = object : StringRequest(Request.Method.POST, url,
                        //klo berhasil
                        Response.Listener
                        {
                            //baca data dari json
                            val obj = JSONObject(it)
                            //klo resultnya OK
                            if (obj.getString("result") == "OK") {
                                //klo result dari jsonnya okay, maka berarti berhasil insert data
                                Toast.makeText(this, "Selamat, Akun anda berhasil dibuat. Silahkan login!",
                                    Toast.LENGTH_SHORT).show()
                                //klo berhasil balik ke page login
                                val intent = Intent(this, SignIn::class.java)
                                startActivity(intent)
                                finish() //biar gk bisa back lagi
                            } else if (obj.getString("result") == "ERROR"){
                                //baca data dari json mesagenya apa
                                val msg = obj.getString("message")
                                //tampilin di toast pesannya apa
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        },
                        Response.ErrorListener {
                            Log.d("apiresult", it.message.toString())
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            //kirim data yang diinput user
                            params["fname"] = fname
                            params["lname"] = lname
                            params["username"] = username
                            params["password"] = password
                            return params
                        }
                    }
                    q.add(stringRequest) }
                else {
                    Toast.makeText(this, "Password yang Anda masukkan tidak sama!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                //tampilin toast klo gk lengkap
                Toast.makeText(this,"Silahkan lengkapi data diri Anda!",Toast.LENGTH_SHORT).show()
            }
        }
    }

}