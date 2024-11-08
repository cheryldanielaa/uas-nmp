package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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


        //kirim cabang dalam bentuk objek
        //cabang yang di dalam kurung itu cabang list yang dideclare diatas
        var cabangFragmentList = WhatWePlayFragment.newInstance(cabangs)
        supportFragmentManager.beginTransaction().apply{
            add(R.id.container, cabangFragmentList) //yang mau dicantolin tuh event fragment list yang sdh dideclare di atas kiw kiw
            commit()
        }
    }
}