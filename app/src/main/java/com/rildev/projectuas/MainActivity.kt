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
import com.google.android.material.snackbar.Snackbar
import com.rildev.projectuas.databinding.ActivityMainBinding
import com.rildev.projectuas.databinding.DrawerLayoutBinding

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
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        //atur viewpager, masukin what we play ke dalem list, biar nnti dia
        //keintegrasi trs bs digeser"
        val fragments: ArrayList<Fragment> = ArrayList()
        //urutan array lis ini nentuin urutan fragmentnya yg pertama mana, kedua mana

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
            binding.mainActivity.viewPager.currentItem = when (it.itemId) {
                R.id.WhatWePlay -> 0 //klo index ke 0 di bottomNav=0, maka what we play
                R.id.OurSchedule -> 1 //klo index ke 1 di bottomNav=1, maka  our schedule
                R.id.WhoWeAre -> 2 //klo index ke 2 di bottomNav=2, maka who we are
                else -> 0 // default to home
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
                    //GAK BISA SIGN OUT, SHARED PREFERENCES GAK KEHAPUS SELAMANYA
                    Toast.makeText(this,"sedihhh",Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}