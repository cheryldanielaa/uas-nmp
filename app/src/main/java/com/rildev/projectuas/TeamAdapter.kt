package com.rildev.projectuas

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.databinding.TeamListBinding
import com.rildev.projectuas.TeamBank
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding

class TeamAdapter(private var teamList: List<TeamBank>):RecyclerView.Adapter<TeamAdapter.TeamViewHolder>()  {
    class TeamViewHolder(val binding:TeamListBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamListBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.txtNamaTeam.text = teamList[position].nama
        holder.binding.txtNamaTeam.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val selectedTim=teamList[position].nama
            var memberList=MemberData.member.toMutableList()
            memberList=memberList.filter { it.teamName.nama == selectedTim }.toMutableList()
            val intent=Intent(activity, MemberPageList::class.java)
            intent.putParcelableArrayListExtra(
                MemberPageList.TEAM,
                ArrayList(memberList)
            )
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }


}
