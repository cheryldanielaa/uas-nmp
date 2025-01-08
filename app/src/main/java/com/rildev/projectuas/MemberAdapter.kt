package com.rildev.projectuas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.databinding.MemberListBinding
import com.squareup.picasso.Picasso

class MemberAdapter(private var memberList: ArrayList<MemberBank>) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {
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
        holder.binding.txtNamaMember.text = memberList[position].nickname
        holder.binding.txtRole.text = memberList[position].role

        //loading gambar pake picasso
        val urlGambar = memberList[position].gambarmember; //url gambar

        //gunakan picasso untuk nampilin gambar
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(urlGambar).into(holder.binding.memberProfile)
    }


}