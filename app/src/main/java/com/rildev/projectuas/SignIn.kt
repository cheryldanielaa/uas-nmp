package com.rildev.projectuas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivitySignInBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    lateinit var user : User //deklarasi object user

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //simpan login state disini?
        var login_state = false //basicnya false

        //buat sharedpreferencesnya disini
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        login_state = sharedPreferences.getBoolean(
            "LOGIN_STATE",
            false
        ) //basicnya false

        //cek apakah bisa login atau nggak
        binding.btnSignIn.setOnClickListener {
            //ambil value dari username dan password user
            var usn = binding.txtUsername.text.toString()
            var pass = binding.txtPassword.text.toString()

            //klo usn sama password tdk kosong
            if (usn.isNotEmpty() && pass.isNotEmpty()) {
                //urus ke database
                val q = Volley.newRequestQueue(this) //karena layout sign in itu activity, maka pake this

                //panggil url dimana apinya dibuat
                val url = "https://ubaya.xyz/native/160422026/project/login.php"

                //waktu login itu kita kirim data, jd hrus pake anonymous object
                val stringRequest = object : StringRequest(Request.Method.POST, url,
                    //klo berhasil
                    Response.Listener
                    {
                        //baca data dari json
                        val obj = JSONObject(it)

                        //klo resultnya OK
                        if (obj.getString("result") == "OK") {
                            //klo diliat dari json viewer, objek besarnya namanya data
                            val data = obj.getJSONArray("data")

                            //karena outputnya pasti cuman satu maka ambil index ke 0
                            val userJson = data.getJSONObject(0)
                            val idmember = userJson.getInt("idmember")
                            val fname = userJson.getString("fname")
                            val lname = userJson.getString("lname")
                            val fullName = "$fname $lname"

                            //untuk mendapatkan object user yang lagi login
                            val sType = object : TypeToken<User>() {}.type

                            //baca data user dari json
                            user = Gson().fromJson<User>(userJson.toString(), sType)
                            Log.d("apiresult", user.toString())

                            //update shared preferencesnya >> dari semula false jadi true
                            //klo true maka update shared preferencesnya
                            login_state = true //login statenya jadi true (buat simpen status loginnya)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("LOGIN_STATE", login_state)
                            editor.putInt("user_id", idmember)
                            editor.putString("user_full_name", fullName)
                            editor.apply()

                            Toast.makeText(this, "Selamat datang, $fullName!", Toast.LENGTH_SHORT).show()

                            //klo benar maka muncul main activity sama bikin user shared preferences
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() //biar gk bisa back lagi
                        } else {
                            Toast.makeText(
                                this,
                                "Maaf, username/password yang Anda masukkan salah!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"] = usn //kirim username yang diinput user
                        params["password"] = pass //kirim password yang diinput user
                        return params
                    }
                }
                q.add(stringRequest)
            } else {
                Toast.makeText(this, "Silahkan isi username dan password Anda!", Toast.LENGTH_SHORT).show()
            }
        }

        //nampilin logo di sign in
        val url = "https://ubaya.xyz/native/160422026/project/aboutus.php"
        val q = Volley.newRequestQueue(this)

        //objek StringRequest punya 4 parameter -> 1. request method || 2. url || 3. listener kalo sukses -> if server status OK || 4. listener kalo error
        var stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("apiresult", it) //it contains JSON string from API
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    val sType = object : TypeToken<List<AboutUs>>() {}.type
                    val aboutUs: List<AboutUs> = Gson().fromJson(data.toString(), sType)

                    val first = aboutUs[0]

                    binding.txtNama.text = first.name

                    val imageUrl = first.photo
                    val builder = Picasso.Builder(binding.root.context)
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(imageUrl).into(binding.imgLogo)

                    val editor = sharedPreferences.edit()
                    editor.putString("user_logo", imageUrl)
                    editor.apply()

                    Log.d("cekisiarray", aboutUs.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)

        //klo login statenya true (shared preferences), maka dia langsung ke menu utama
        if (login_state == true) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() //biar gk bs ngeback
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish() //biar gk bs ngeback
        }
    }
}


