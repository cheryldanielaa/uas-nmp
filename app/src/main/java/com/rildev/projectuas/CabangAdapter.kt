package com.rildev.projectuas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.CabangListBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

//terima data array yang dikirimkan dari what we play fragment
class CabangAdapter(private val cabangs: ArrayList<Cabang>):RecyclerView.Adapter<CabangAdapter.CabangViewHolder>() {
    //buat list array
    private var timList = ArrayList<TeamBank>()
    private var achievementList = ArrayList<Achievement>()
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

    override fun onBindViewHolder(holder: CabangViewHolder, position: Int)
    {
        //ini code buat apa yang mau ditampilin di setiap card
        val urlGambar = cabangs[position].gambar; //url gambar

        //gunakan picasso untuk nampilin gambar
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(urlGambar).into(holder.binding.logoCabang)

        with(holder.binding) { //tampilin nama cabang dan descriptionnya apa
            txtNamaCabang.text = cabangs[position].name
            txtGameDesc.text = cabangs[position].description

            //atur button teams
            btnTeams.setOnClickListener {
                val q = Volley.newRequestQueue(holder.itemView.context)
                //masukin link ubaya xyz
                val url = "https://ubaya.xyz/native/160422026/project/gettimcabang.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener {
                        //baca data dari json
                        val obj = JSONObject(it)
                        //klo resultnya OK
                        if (obj.getString("result") == "OK") {
                            //klo diliat dari json viewer, objek besarnya namanya data
                            val data = obj.getJSONArray("data")

                            val sType = object : TypeToken<ArrayList<TeamBank>>() {}.type

                            //baca data user dari json
                            timList = Gson().fromJson(data.toString(), sType)
                            Log.d("apiresult",timList.toString())

                            //ini atur buat munculin team page
                            val activity = holder.itemView.context as Activity
                            val intent = Intent(activity, TeamPageList::class.java)
                            //kirim value ke team page lewat companion object TT
                            intent.putParcelableArrayListExtra(
                                TeamPageList.DAFTARTIM,
                                timList
                            )
                            activity.startActivity(intent)
                        }
                    },
                    Response.ErrorListener {
                        Log.d("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        //pake holder adapter position
                        params["id"] = cabangs[holder.adapterPosition].idgame.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }

            //atur button achievements
            btnAchievement.setOnClickListener {
                val q = Volley.newRequestQueue(holder.itemView.context)
                //masukin link ubaya xyz
                val url = "https://ubaya.xyz/native/160422026/project/getachievementcabang.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener {
                        //baca data dari json
                        val obj = JSONObject(it)
                        //klo resultnya OK
                        if (obj.getString("result") == "OK") {
                            //klo diliat dari json viewer, objek besarnya namanya data
                            val data = obj.getJSONArray("data")

                            val sType = object : TypeToken<ArrayList<Achievement>>() {}.type

                            //baca data user dari json
                            achievementList = Gson().fromJson(data.toString(), sType)
                            Log.d("halonmp",achievementList.toString())

                            //ini atur buat munculin team page
                            val activity = holder.itemView.context as Activity
                            val intent = Intent(activity, AchievementDetails::class.java)
                            //kirim value ke team page lewat companion object TT
                            intent.putParcelableArrayListExtra(
                                AchievementDetails.CABANG_ACHIEVEMENT,
                                achievementList
                            )
                            activity.startActivity(intent)
                        }
                    },
                    Response.ErrorListener {
                        Log.d("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        //pake holder adapter position
                        params["id"] = cabangs[holder.adapterPosition].idgame.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }
    }
}