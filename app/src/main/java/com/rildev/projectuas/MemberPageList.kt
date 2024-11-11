package com.rildev.projectuas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rildev.projectuas.databinding.ActivityMemberPageListBinding

class MemberPageList : AppCompatActivity() {
    companion object
    {
        //kirim value object
        var NAMA_TIM = "namaTim"
        var TEAM = "team"
    }
    private lateinit var binding: ActivityMemberPageListBinding
    private var member:ArrayList<MemberBank> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberPageListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        member= intent.getParcelableArrayListExtra<MemberBank>(TEAM)?: ArrayList()
        var memberAdapter = MemberAdapter(member)
        var logoCabang = member[0].teamName.cabang.logo_gambar
        var namaTeam = member[0].teamName.nama

        binding.imgLogo.setImageResource(logoCabang)
        binding.txtTeams.text = namaTeam
        binding.recMember.adapter = memberAdapter
        binding.recMember.layoutManager = LinearLayoutManager(this)
        binding.recMember.setHasFixedSize(true)
    }
}