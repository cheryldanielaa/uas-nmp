package com.rildev.projectuas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding

//KEY EVENTS ini sbg companion object utk menerima daftar object
//yang dikirimkan

private const val KEY_EVENTS="cabang lomba"

//krn kita tampilin data di recycler view, jd gak pake list fragment, tp fragment biasa
class WhatWePlayFragment : Fragment() {
    //What We Play ini menyimpan list dari cabang yang ada
    //ada button teams dan achievement dimana nantinya dia bisa kirim data tsb
    //fungsinya klo di week 8 itu sama kayak EventListFragment
    //array kosongan utk menampung data yang dikirimkan dari main activity
    private var cabangs:ArrayList<Cabang> = ArrayList()

    //deklarasikan binding
    private lateinit var binding:FragmentWhatWePlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        arguments?.let {
            //terima cabang dari mainactivity trs disimpan di list kosong yang udah
            //dideclare diatas
            cabangs =it.getParcelableArrayList<Cabang>(KEY_EVENTS) as ArrayList<Cabang>
        }
    }

    //klo dia ada desainnya, maka tampilkan desain fragmentnya coii
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)
        binding.recCabang.layoutManager = LinearLayoutManager(requireContext())
        //kirimin parameter listcabang ke cabang adapter
        binding.recCabang.adapter = CabangAdapter(cabangs)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(cabangs:ArrayList<Cabang>) =
            WhatWePlayFragment().apply {
                arguments = Bundle().apply {
                    //ini biar bisa ngirim objek utuh
                    //gak per data kek sblmnya
                    //parameter kedua ini sesuai yang di constructor
                    putParcelableArrayList(KEY_EVENTS, cabangs)
                }
            }
    }
}