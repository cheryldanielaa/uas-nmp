package com.rildev.projectuas

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.databinding.CabangListBinding

//terima data array yang dikirimkan dari what we play fragment
class CabangAdapter(private val cabangs: ArrayList<Cabang>):RecyclerView.Adapter<CabangAdapter.CabangViewHolder>() {
    //view holder kelasnya dibuat di dalam
    //cmn bs digunakan di dlm adapter
    //view holder extend recycle >> dimana recycle itu parentnya
    //artinya view pny akses ke UI, dmn cabang list binding itu file xml cardnya
    class CabangViewHolder(val binding:CabangListBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabangViewHolder {
        //digunakan utk membuat object CabangListViewHolder
        val binding = CabangListBinding.inflate(
            LayoutInflater.
            from(parent.context),parent,false)
        return CabangViewHolder(binding) //ni buat cardnya
    }

    override fun getItemCount(): Int {
        //buat tau size dari array cabang ada brp
        return cabangs.size
    }

    override fun onBindViewHolder(holder: CabangViewHolder, position: Int) {
        //menghubungkan layout dgn data (array question)
        //fungsi ini dipanggil sebanyak item yg ingin ditampilkan
        //poisition itu mksdnya indexnya brp (berdasarkan array)
        //nama custom layout cardview diwakilkan oleh holder
        //holder itu di cabangviewholder >> isi elemen di card
        holder.binding.txtNamaCabang.text=cabangs[position].namaCabang
        holder.binding.txtGameDesc.text=cabangs[position].desc
        holder.binding.logoCabang.setImageResource(cabangs[position].logo_gambar)

        //atur supaya button itu bisa klo klik kirim value
        holder.binding.btnAchievement.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val selectedCabang = cabangs[position].namaCabang
            var achievementlist = AchievementData.achievement.toMutableList()
            //filter supaya yang dikirim hanya achievement sesuai cabang yang diklik aja
            achievementlist = achievementlist.filter { it.cabangLomba.namaCabang == selectedCabang }.toMutableList()
            val intent = Intent(activity, AchievementDetails::class.java)
            intent.putParcelableArrayListExtra(AchievementDetails.CABANG_ACHIEVEMENT, ArrayList(achievementlist))
            activity.startActivity(intent)
        }
//


        holder.binding.btnTeams.setOnClickListener {
            val activity=holder.itemView.context as Activity
            val selectedCabang=cabangs[position].namaCabang
            var teamlist=TeamData.timList.toMutableList()
            teamlist=teamlist.filter { it.cabang.namaCabang == selectedCabang }.toMutableList()
            val intent=Intent(activity, TeamPageList::class.java)
            intent.putParcelableArrayListExtra(
                TeamPageList.CABANG,
                ArrayList(teamlist)
            )
            activity.startActivity(intent)
        }
    }
}