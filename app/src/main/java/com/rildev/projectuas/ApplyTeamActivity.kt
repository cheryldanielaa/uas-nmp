package com.rildev.projectuas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rildev.projectuas.databinding.ActivityApplyTeamBinding
import org.json.JSONObject

class ApplyTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityApplyTeamBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        var idGame=0
        var idTeam=0
        setContentView(binding.root)
        val listGame=mutableListOf<Pair<Int, String>>()
        val listTeam=mutableListOf<Pair<Int, String>>()
        val qGem=
            Volley.newRequestQueue(this) //karena layout sign in itu activity, maka pake this
        //panggil url dimana apinya dibuat
        val urlGem="https://ubaya.xyz/native/160422026/project/getcabang.php"
        val stringRequestGem=object : StringRequest(
            Request.Method.POST,
            urlGem,
            //klo berhasil
            Response.Listener
            {
                //baca data dari json
                val obj=JSONObject(it)
                Log.d("RESULTT", obj.getString("result"))
                //klo resultnya OK
                if (obj.getString("result") == "OK") {
                    val dataArray=obj.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        idGame=dataArray.getJSONObject(i).getInt("idgame")
                        val nameGame=dataArray.getJSONObject(i).getString("name")
//                        Log.d("micheleGame", nameGame)
                        listGame.add(Pair(idGame, nameGame))

                    }
                    val adapterGem=ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        listGame.map { it.second }
                    )
                    binding.spinnerGame.adapter=adapterGem

                } else if (obj.getString("result") == "ERROR") {
                    //baca data dari json mesagenya apa
                    val msg=obj.getString("message")
                    //tampilin di toast pesannya apa
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.d("apiresult", it.message.toString())
            }) {

        }
        qGem.add(stringRequestGem)
        binding.spinnerGame.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedGameId=listGame[position].first // Mendapatkan id dari listGame
                val qTim=
                    Volley.newRequestQueue(this@ApplyTeamActivity) //karena layout sign in itu activity, maka pake this
                //panggil url dimana apinya dibuat
                val urlTim="https://ubaya.xyz/native/160422026/project/getteam.php"
                val stringRequestTim=object : StringRequest(
                    Request.Method.POST,
                    urlTim,
                    //klo berhasil
                    Response.Listener
                    {
                        //baca data dari json

                        val objTim=JSONObject(it)
                        Log.d("BAPAKKAU", it)
                        //klo resultnya OK
                        if (objTim.getString("result") == "OK") {
                            listTeam.clear()
                            val dataArrayTim=objTim.getJSONArray("data")
                            for (i in 0 until dataArrayTim.length()) {
                                idTeam=dataArrayTim.getJSONObject(i).getInt("idteam")
                                val nameTeam=dataArrayTim.getJSONObject(i).getString("name")
                                listTeam.add(Pair(idTeam, nameTeam))

                            }
                            val adapterTim=ArrayAdapter(
                                this@ApplyTeamActivity,
                                android.R.layout.simple_list_item_1,
                                listTeam.map { it.second }
                            )
                            adapterTim.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spinnerTeam.adapter=adapterTim

                        } else if (objTim.getString("result") == "ERROR") {
                            //baca data dari json mesagenya apa
                            val msgTim=objTim.getString("message")
                            //tampilin di toast pesannya apa
                            Toast.makeText(this@ApplyTeamActivity, msgTim, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params=HashMap<String, String>()
                        params["idgame"]=selectedGameId.toString() // Kirim idGame sebagai parameter
                        Log.d("Lalala", params.toString())
                        return params
                    }
                }
                Log.d("Kirim PHP", "Berhasil kirim ke php")
                qTim.add(stringRequestTim)

                // Gunakan selectedGameId untuk keperluan selanjutnya, misalnya:
//                Log.d("Selected Game ID", selectedGameId.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.btnApply.setOnClickListener {
            val selectedGamePosition=binding.spinnerGame.selectedItemPosition
            val selectedGameID=listGame[selectedGamePosition].first

            val selectedTeamPosition=binding.spinnerTeam.selectedItemPosition
            val selectedTeamID=listTeam[selectedTeamPosition].first

            val desc=binding.txtDescription.text.toString()

            if (desc.isBlank()) {
                Toast.makeText(this, "Description tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userId = intent.getIntExtra("user_id", -1)
            Log.d("INI ID MEMBER", userId.toString())

            if(userId!=-1){
                val queueApply=Volley.newRequestQueue(this)
                val urlApply="https://ubaya.xyz/native/160422026/project/applyTeam.php"
                val stringRequestTim=object : StringRequest(
                    Request.Method.POST,
                    urlApply,
                    //klo berhasil
                    Response.Listener
                    {
                        //baca data dari json

                        val objApply=JSONObject(it)
                        //klo resultnya OK
                        if (objApply.getString("result") == "OK") {
                            Toast.makeText(this, "Apply team berhasil", Toast.LENGTH_SHORT).show()

                        } else if (objApply.getString("result") == "ERROR") {
                            //baca data dari json mesagenya apa
                            val msgTim=objApply.getString("message")
                            //tampilin di toast pesannya apa
                            Toast.makeText(this@ApplyTeamActivity, msgTim, Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params=HashMap<String, String>()
                        params["idgame"]=selectedGameID.toString() // Kirim idGame sebagai parameter
                        params["idteam"]=selectedTeamID.toString()
                        params["description"]=desc
                        return params
                    }
                }
                queueApply.add(stringRequestTim)
            }
            else{
                Toast.makeText(this, "ID member tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }
}