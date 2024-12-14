package com.rildev.projectuas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityMainBinding
import com.rildev.projectuas.databinding.DrawerHeaderBinding
import com.rildev.projectuas.databinding.DrawerLayoutBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: DrawerLayoutBinding
    //deklarasi semua list yang dipake buat fragment itu disini
    //deklarasikan list dari cabang yang ada

    //NOTES :
    //Basenya utk What We Play, Who We Are, sama Our Schedule itu di Main Activity
    //What We Play dan teman-temannya itu Fragment List
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        val loginState = sharedPreferences.getBoolean("LOGIN_STATE", false)

        //meskipun udh hapus sharedPreferences di signout, ttp hrs checking lg loginState apa biar ga lgsg msk mainActivity otomatis
        if (!loginState) {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
            return
        }

        //deklarasikan view binding
        //karena dia pake drawer maka bindingnya jadi di drawerlayout
        binding = DrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set ada toolbar di atas
        setSupportActionBar(binding.mainActivity.menuToolbar)

        //tambahin hamburger wow
        supportActionBar?.setDisplayHomeAsUpEnabled(false); //krn mau pke hamburger icon buat ke navbar makanya set false

        //buat hubungin drawer ke playlist detailnya
        var drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            binding.mainActivity.menuToolbar, R.string.app_name, R.string.app_name
        )

        //biar nama app e ga kluar sbelah e-sport T.T
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        //atur viewpager, masukin what we play ke dalem list, biar nnti dia keintegrasi trs bs digeser"
        val fragments: ArrayList<Fragment> = ArrayList()
        //urutan array list ini nentuin urutan fragmentnya yg pertama mana, kedua mana

        //klo mau kirimin value masukin disini, jangan lewat begintransaction karena fungsinya sama aja
        //klo kamu pake begin transaction jd numpuk gak jelas, trs dia gak bisa nggeser
        fragments.add(WhatWePlayFragment())
        fragments.add(OurScheduleFragment())
        fragments.add(WhoWeAreFragment())

        binding.mainActivity.viewPager.adapter = MyAdapter(this, fragments) //hubungin sm

        //ini buat ngecode klo viewpagernya digeser, menu item yang dihover ikut geser
        binding.mainActivity.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            //fungsi ini akan dijalankan setiap kali pagenya berubah
            override fun onPageSelected(position: Int) {
                //ini buat ambil id dari page sama id dari navmenu
                //jd klo kita geser pagenya, nav menunya jg berubah (yg kehover)
                binding.mainActivity.bottomNav.selectedItemId =
                    binding.mainActivity.bottomNav.menu.getItem(position).itemId
            }
        })

        binding.mainActivity.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.WhatWePlay -> {
                    // Jika item di bottom navigation dipilih sebagai "What We Play"
                    binding.mainActivity.viewPager.currentItem = 0
                    binding.mainActivity.toolbarTitle.text = "E-Sports"
                }
                R.id.OurSchedule -> {
                    // Jika item di bottom navigation dipilih sebagai "Our Schedule"
                    binding.mainActivity.viewPager.currentItem = 1
                    binding.mainActivity.toolbarTitle.text = "Our Schedule"
                }
                R.id.WhoWeAre -> {
                    // Jika item di bottom navigation dipilih sebagai "Who We Are"
                    binding.mainActivity.viewPager.currentItem = 2
                    binding.mainActivity.toolbarTitle.text = "Who We Are"
                }
                else -> {
                    //default balik ke home HEHEHE
                    binding.mainActivity.viewPager.currentItem = 0
                    binding.mainActivity.toolbarTitle.text = "E-Sports"
                }
            }
            true
        }

        //atur buat apa yang terjadi klo apply team dan sign out diklik
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                //klo klik apply team, buka page apply team activity
                R.id.itemApplyTeam -> {
                    val intent = Intent(this, ProposalListActivity::class.java)
                    startActivity(intent)
                }

                R.id.itemSignOut -> {
                    //hapus sharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("LOGIN_STATE", false)
                    editor.apply()

                    //hbs signout, buka signin
                    val intent = Intent(this, SignIn::class.java)
                    startActivity(intent)
                    finish()

                    Toast.makeText(this,"Berhasil Sign Out uhuy",Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        //tampilin Welcome, Andrew Ng anjai
        var fullName = sharedPreferences.getString("user_full_name", "User") //kalo error kluarin "User"
        fullName = fullName?.uppercase()

        val headerBinding = DrawerHeaderBinding.bind(binding.navView.getHeaderView(0))
        headerBinding.txtNamaPengguna.text = "Welcome, $fullName"

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

                    val imageUrl = first.photo
                    val builder = Picasso.Builder(binding.root.context)
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(imageUrl).into(headerBinding.imgLogoApp)

                    Log.d("cekisiarray", aboutUs.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }
}