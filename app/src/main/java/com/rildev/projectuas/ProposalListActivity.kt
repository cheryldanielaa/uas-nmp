package com.rildev.projectuas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityProposalListBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ProposalListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProposalListBinding
    private var proposal:ArrayList<Proposal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProposalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFAB.setOnClickListener {
            val intent = Intent(this, ApplyTeamActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 1)

        val q = Volley.newRequestQueue(this) //krn dia activity

        //masukin link ubaya xyz
        val url = "https://ubaya.xyz/native/160422026/project/getproposal.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            //klo berhasil
            Response.Listener {
                //baca data dari json
                val obj = JSONObject(it)

                //klo resultnya OK
                if (obj.getString("result") == "OK") {
                    val dataArray=obj.getJSONArray("data")
                    val propType = object : TypeToken<List<Proposal>>() {}.type
                    val proposal = Gson().fromJson(dataArray.toString(), propType) as ArrayList<Proposal>
                    binding.recViewProposal.layoutManager = LinearLayoutManager(this)
                    binding.recViewProposal.adapter = ProposalAdapter(proposal)
                }
                else if (obj.getString("result") == "ERROR") {
                    //baca data dari json mesagenya apa
                    val msgTim=obj.getString("message")
                    //tampilin di toast pesannya apa
                    Toast.makeText(this, msgTim, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.d("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                //pake holder adapter position
                params["idmember"] = userId.toString() //kirim idmember yang login
                return params
            }
        }
        q.add(stringRequest)
    }

    //klo misal habis ngisi apply team, kan dia balik ke proposal list lagi, klo pencet back, proposal list muncul lg kyk double karna apply team di finish()
    //biar ga double, tambahin finish() pas pencet back
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
