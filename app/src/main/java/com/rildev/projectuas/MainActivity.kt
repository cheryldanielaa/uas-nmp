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
    var cabangs: ArrayList<Cabang> = arrayListOf(
        Cabang("Valorant",R.drawable.valorant,"Valorant is a tactical first-person " +
                "shooter from Riot Games that " +
                "combines precise gunplay with unique character abilities. " +
                "Players engage in strategic, team-based matches where they must " +
                "complete objectives and outsmart opponents to win."),
        Cabang("Mobile Legends",R.drawable.ml,"Mobile Legends: Bang Bang is " +
                "an exciting multiplayer online battle arena " +
                "(MOBA) game from Moonton that blends fast-paced action with strategic gameplay. " +
                "Players form teams of heroes, each with unique skills and abilities, to engage in thrilling " +
                "5v5 matches. Cooperation and tactics are essential as teams battle to destroy the enemy's " +
                "base while defending their own, making every match a test of skill and teamwork."),
        Cabang("League of Legends",R.drawable.lol,"League of Legends is a dynamic multiplayer " +
                "online battle arena (MOBA) game developed by Riot Games that combines strategy, teamwork, " +
                "and fast-paced action. Players choose from a diverse roster of champions, " +
                "each with unique abilities, to engage in strategic 5v5 matches. Teams must work together " +
                "to outmaneuver opponents, secure objectives, and ultimately destroy the enemy Nexus, " +
                "making every game a thrilling test of skill and coordination.")
    )
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