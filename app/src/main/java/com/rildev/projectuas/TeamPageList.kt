package com.rildev.projectuas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rildev.projectuas.databinding.ActivityAchievementDetailsBinding
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding
import com.rildev.projectuas.databinding.ActivityTeamPageListBinding

private const val KEY_EVENTS="cabang lomba"
class TeamPageList : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPageListBinding

    private var tim:ArrayList<TeamBank> = ArrayList()
    companion object
    {
        //kirim value object
        var NAMA_TIM = "namaTim"
        var CABANG = "cabang"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityTeamPageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

         //parameter buat dipake di TeamAdapter
        tim = intent.getParcelableArrayListExtra<TeamBank>(CABANG)?: ArrayList()
        var teamAdapter = TeamAdapter(tim)
        var namaCabang=tim[0].cabang.namaCabang
        var logoCabang = tim[0].cabang.logo_gambar
        binding.recTeam.adapter = teamAdapter
        binding.recTeam.layoutManager = LinearLayoutManager(this)
        binding.recTeam.setHasFixedSize(true)
        binding.imgCabang.setImageResource(logoCabang)
    }
}