package com.rildev.projectuas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.TeamAdapter.TeamViewHolder
import com.rildev.projectuas.databinding.MemberListBinding
import com.rildev.projectuas.databinding.TeamListBinding
import java.lang.reflect.Member

class MemberAdapter(private var memberList: List<MemberBank>) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {
    class MemberViewHolder(val binding: MemberListBinding):
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = MemberListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MemberViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.binding.txtNamaMember.text = memberList[position].nickame
        holder.binding.txtRole.text = memberList[position].role
        holder.binding.memberProfile.setImageResource(memberList[position].avatarId)
    }


}