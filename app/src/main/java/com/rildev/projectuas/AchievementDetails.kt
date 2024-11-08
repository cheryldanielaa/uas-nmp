package com.rildev.projectuas

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rildev.projectuas.databinding.ActivityAchievementDetailsBinding


class AchievementDetails : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementDetailsBinding
    companion object
    {
        var CABANG_ACHIEVEMENT = "pencapaian cabang"
    }
    private var  achievement:ArrayList<Achievement> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //retrieve value dari object yang dikirimkan dari what we play yang dipilih
        achievement=intent.getParcelableArrayListExtra<Achievement>(CABANG_ACHIEVEMENT) ?: ArrayList()
        //karena dia udah difilter, jadi otomatis yang muncul cmn cabang yang sama aja
        //jadi ambil aja 1 perwakilan data EAAA
        var namaCabang = achievement[0].cabangLomba.namaCabang
        var logoCabang = achievement[0].cabangLomba.logo_gambar

        //set gambarnya
        binding.txtNama.text = namaCabang
        binding.gambarLogo.setImageResource(logoCabang)


        //ini list untuk bikin tahunnya
        //list udh kefilter menurut cabang
        var listTahun = mutableSetOf<String>() //Menggunakan set utk menghindari duplikat
        listTahun.add("All")
        for (ach in achievement) {
            //tambahin ke set
            listTahun.add(ach.year.toString())
        }
        //atur year dan sort secara descending
        val listSorted = listTahun.toList().sorted().
        let { listOf("All") + it.filter { it != "All" } } //solusinya all ditaruh di paling atas,
        //sisanya diurutin secara ascending, tanpa melibatkan all

        //buat objek array adapter buat ambil data dr achievement data
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listSorted.toList() //ubah set ke list
        )
        binding.spinnerYear.adapter = adapter

        val listAchievementKhusus = mutableSetOf<String>()
        //atur code biar waktu tiap kali itemnya di klik, outputnya brubah
        binding.spinnerYear.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedYear = parent.getItemAtPosition(position).toString()
                var dataAcara=""
                var i=0 //buat ngeloop jumlah

                //delete tiap kli ngeloop
                if(selectedYear=="All")
                {
                    listAchievementKhusus.clear()
                    for (ach in achievement) {
                           listAchievementKhusus.add(ach.desc + " ("+ach.year + ") "+
                           "- "+ach.tim.nama)
                    }
                    for (data in listAchievementKhusus)
                    {
                        i+=1
                        dataAcara+=i.toString() +". "+data +"\n"
                    }
                    binding.txtAchievement.text=dataAcara
                }
                else
                {
                    listAchievementKhusus.clear()
                    for (ach in achievement) {
                        if(ach.year.toString() == selectedYear)
                        {
                            listAchievementKhusus.add(ach.desc + " ("+ach.year + ") "+
                                    "- "+ach.tim.nama)
                        }
                    }
                    for (data in listAchievementKhusus)
                    {
                        i+=1
                        dataAcara+=i.toString() +". "+data+"\n"
                    }
                    binding.txtAchievement.text=dataAcara
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }}