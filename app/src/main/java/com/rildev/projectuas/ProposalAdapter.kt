package com.rildev.projectuas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.TeamAdapter.TeamViewHolder
import com.rildev.projectuas.databinding.ProposalListBinding
import com.rildev.projectuas.databinding.TeamListBinding

class ProposalAdapter (private var proposalList: List<Proposal>): RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {
    class ProposalViewHolder(val binding: ProposalListBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalViewHolder {
        val binding = ProposalListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ProposalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProposalAdapter.ProposalViewHolder, position: Int) {
        holder.binding.txtCabang.text = proposalList[position].namaCabang
        holder.binding.txtStatus.text = proposalList[position].status
    }

    override fun getItemCount(): Int {
        return proposalList.size
    }
}
