package com.rildev.projectuas

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import org.json.JSONObject
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rildev.projectuas.databinding.TeamListBinding
import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.TeamBank
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding

class TeamAdapter(private var teamList: List<TeamBank>):RecyclerView.Adapter<TeamAdapter.TeamViewHolder>()  {
    //buat array untuk nampung nama tim member
    private var tim_member = ArrayList<MemberBank>()
    class TeamViewHolder(val binding:TeamListBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamListBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.txtNamaTeam.text = teamList[position].name

        //querynya sama kayak yg di cabang mau ke tim
        holder.binding.txtNamaTeam.setOnClickListener {
            //holder itemview karna di recycler view
            val q = Volley.newRequestQueue(holder.itemView.context)

            //masukin link ubaya xyz
            val url = "https://ubaya.xyz/native/160422026/project/getmembertim.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                //klo berhasil
                Response.Listener {
                    //baca data dari json
                    val obj = JSONObject(it)

                    //klo resultnya OK
                    if (obj.getString("result") == "OK") {
                        //klo diliat dari json viewer, objek besarnya namanya data
                        val data = obj.getJSONArray("data")

                        //team members itu page yang simpen hasil inner join hehehehe
                        val sType = object : TypeToken<ArrayList<MemberBank>>() {}.type

                        //baca data user dari json
                        tim_member = Gson().fromJson(data.toString(), sType)
                        Log.d("meong",tim_member.toString())

                        //ini atur buat munculin ke member
                        val activity = holder.itemView.context as Activity
                        val intent=Intent(activity, MemberPageList::class.java)
                        intent.putParcelableArrayListExtra(
                        MemberPageList.LIST_MEMBER, tim_member)
                        activity.startActivity(intent)
                    }
                },
                Response.ErrorListener {
                    Log.d("apiresult", it.message.toString())
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    //pake holder adapter position >> cari idteamnya
                    params["id"] = teamList[holder.adapterPosition].idteam.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
    override fun getItemCount(): Int {
        return teamList.size
    }

}
