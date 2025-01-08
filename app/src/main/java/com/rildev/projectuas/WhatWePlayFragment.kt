package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding
import org.json.JSONObject


class WhatWePlayFragment : Fragment() {
    //array kosong untuk menampung cabang
    private var cabangs:ArrayList<Cabang> = ArrayList()
    //deklarasikan binding
    private lateinit var binding:FragmentWhatWePlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        //di dalem oncreate ini tampilkan hubungan sama si volley lov
        val q = Volley.newRequestQueue(activity) //karena dia fragment, maka ambil context dr activity/induknya

        val url = "https://ubaya.xyz/native/160422026/project/getcabang.php"; //masukin url ubaya xyz disini

        //buat webservice url >> semuanya dijalankan disini
        var stringRequest = StringRequest(
            Request.Method.POST, url, //pastikan yang diimport request punya volley
            {
                //code ini dijalankan klo sukses
                Log.d("apiresult", it) //mengandung string json dari API
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data") //ambil dulu array besar (data), dilihat dari struktur json viewer [..]
                    val sType = object : TypeToken<List<Cabang>>() { }.type //dapet token dari list cabang
                    cabangs = Gson().fromJson(data.toString(), sType) as //param 1 data arraynya, param 2 jenisnya apa
                            ArrayList<Cabang> //outputnya jadi arraylist of playlists
                    Log.d("apiresult", cabangs.toString()) //keluar bos outputnya HEHE
                    updateList()
                }
            },
            {
                //code ini dijalankan klo gagal/failed/error
                Log.e("apiresult", it.message.toString()) //respon yang didapet klo server nemu halangan
                //apiresult itu kek km search di logcat buat baca pesannya
            })
        q.add(stringRequest) //fungsinya utk print message di logcat buat debugging
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    //code ini digunakan spy dia bisa update tampilan secara berkala
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //code buat update list manager
    fun updateList() {
        val lm = LinearLayoutManager(activity)
        with(binding.recCabang) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CabangAdapter(cabangs) //kirim cabang ke parameter cabang adapter
        }
    }
}