package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //deklarasikan view binding
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //atur viewpager, masukin what we play ke dalem list, biar nnti dia
        //keintegrasi trs bs digeser"
        val fragments: ArrayList<Fragment> = ArrayList()
        //urutan array lis ini nentuin urutan fragmentnya yg pertama mana, kedua mana
        fragments.add(OurScheduleFragment())
        fragments.add(WhatWePlayFragment())
        fragments.add(WhoWeAreFragment())

        binding.viewPager.adapter = MyAdapter(this, fragments) //hubungin sm

        //ini buat ngecode klo viewpagernya digeser, menu item yang dihover ikut geser
        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback()
        {
            //fungsi ini akan dijalankan setiap kali pagenya berubah
            override fun onPageSelected(position: Int) {
                //ini buat ambil id dari page sama id dari navmenu
                //jd klo kita geser pagenya, nav menunya jg berubah (yg kehover)
                binding.bottomNav.selectedItemId =
                    binding.bottomNav.menu.getItem(position).itemId
            }
        })

        //code biar klo menu item diklik, pagenya jg bs pindah, gk cmn dr geser aja
        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.WhatWePlay -> 0 //klo index ke 0 di bottomNav=0, maka what we play
                R.id.OurSchedule -> 1 //klo index ke 1 di bottomNav=1, maka  our schedule
                R.id.WhoWeAre -> 2 //klo index ke 2 di bottomNav=2, maka who we are
                else -> 0 // default to home
            }
            //kirim cabang dalam bentuk objek
            //cabang yang di dalam kurung itu cabang list yang dideclare diatas
            if(binding.viewPager.currentItem==0){
                var cabangFragmentList = WhatWePlayFragment.newInstance(cabangs)
                //pake replace biar dia bs berubah scr dinamis
                supportFragmentManager.beginTransaction().apply{
                    replace(R.id.container, cabangFragmentList) //yang mau dicantolin tuh event fragment list yang sdh dideclare di atas kiw kiw
                    commit()
                }
            }
            //DUMMY BUAT NYOBA DOANG JALAN ATAU NGGA
            //NANTI DISESUAIIN SM CODE KALIANN
            else if(binding.viewPager.currentItem==1){
                var babi = OurScheduleFragment.newInstance("a","b")
                supportFragmentManager.beginTransaction().apply{
                    replace(R.id.container, babi) //yang mau dicantolin tuh event fragment list yang sdh dideclare di atas kiw kiw
                    commit()
                }
            }
            else if(binding.viewPager.currentItem==2){
                var siapaKita = WhoWeAreFragment.newInstance("a","b")
                supportFragmentManager.beginTransaction().apply{
                    replace(R.id.container, siapaKita) //yang mau dicantolin tuh event fragment list yang sdh dideclare di atas kiw kiw
                    commit()
                }
            }
            true

        }


    }
}