package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.rildev.projectuas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //deklarasi semua list yang dipake buat fragment itu disini
    //deklarasikan list dari cabang yang ada


    //NOTES :
    //Basenya utk What We Play, Who We Are, sama Our Schedule itu di Main Activity
    //What We Play dan teman-temannya itu Fragment List
    var cabangs=CabangData.cabangs //akses data dari cabang data
    var schedule: ArrayList<ScheduleBank> = ArrayList(ScheduleData.schedule.toList())
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        //deklarasikan view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //atur viewpager, masukin what we play ke dalem list, biar nnti dia
        //keintegrasi trs bs digeser"
        val fragments: ArrayList<Fragment> = ArrayList()
        //urutan array lis ini nentuin urutan fragmentnya yg pertama mana, kedua mana

        //klo mau kirimin value masukin disini, jangan lewat begintransaction karena fungsinya sama aja
        //klo kamu pake begin transaction jd numpuk gak jelas, trs dia gak bisa nggeser
        fragments.add(WhatWePlayFragment.newInstance(cabangs))
        fragments.add(OurScheduleFragment.newInstance(schedule))
        fragments.add(WhoWeAreFragment.newInstance("a", "b"))

        binding.viewPager.adapter = MyAdapter(this, fragments) //hubungin sm

        //ini buat ngecode klo viewpagernya digeser, menu item yang dihover ikut geser
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //fungsi ini akan dijalankan setiap kali pagenya berubah
            override fun onPageSelected(position: Int) {
                //ini buat ambil id dari page sama id dari navmenu
                //jd klo kita geser pagenya, nav menunya jg berubah (yg kehover)
                binding.bottomNav.selectedItemId =
                    binding.bottomNav.menu.getItem(position).itemId
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when (it.itemId) {
                R.id.WhatWePlay -> 0 //klo index ke 0 di bottomNav=0, maka what we play
                R.id.OurSchedule -> 1 //klo index ke 1 di bottomNav=1, maka  our schedule
                R.id.WhoWeAre -> 2 //klo index ke 2 di bottomNav=2, maka who we are
                else -> 0 // default to home
            }
            true
        }
    }
}