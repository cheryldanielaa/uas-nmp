package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityMemberPageListBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MemberPageList : AppCompatActivity() {
    companion object
    {
        //kirim value object
        var LIST_MEMBER = "INI LIST MEMBER"
    }
    private lateinit var binding: ActivityMemberPageListBinding
    private var member:ArrayList<MemberBank> = ArrayList()
    lateinit var selectedGame:Cabang //utk nampung cabang yg dipilih user
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMemberPageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tampung member yang udh difilter
        member= (intent.getParcelableArrayListExtra<MemberBank>(LIST_MEMBER)?: ArrayList()) as ArrayList<MemberBank>
        var memberAdapter = MemberAdapter(member)
        binding.recMember.adapter = memberAdapter
        binding.recMember.layoutManager = LinearLayoutManager(this)
        binding.recMember.setHasFixedSize(true)

        //sekarang panggil volley buat ngeset gambar biar sesuai hehe
        val idGame = member[0].idgame //ambil idgame krn pasti sama jd perwakilan index 0 aja

        //ambil nama tim
        val namaTim = member[0].teamname
        binding.txtTeams.text=namaTim //set textbox sesuai nama tim

        //jalanin query pake volley
        val q = Volley.newRequestQueue(this) //krn dia activity
        //masukin link ubaya xyz
        val url = "https://ubaya.xyz/native/160422026/project/carigame.php"
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
                    //index 0 krn hasuilnya cmn 1 dan mau ambil objek ke 0 tentunya
                    val cabangJson = data.getJSONObject(0)
                    //untuk mendapatkan object game yg dipilih skrg
                    val sType = object : TypeToken<Cabang>() {}.type
                    //baca data user dari json
                    selectedGame = Gson().fromJson<Cabang>(cabangJson.toString(), sType)
                    Log.d("halocheryl",selectedGame.toString())

                    //klo berhasil maka set bindingnya sesuai url selected game hehe
                    val urlGambar = selectedGame.gambar; //url gambar
                    //gunakan picasso untuk nampilin gambar
                    val builder = Picasso.Builder(this) //pake this krn di activity
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(urlGambar).into(binding.imgLogo) //diload dimana
                }
            },
            Response.ErrorListener {
                Log.d("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                //pake holder adapter position
                params["id"] = idGame.toString() //kirim idgame yang sekarang
                return params
            }
        }
        q.add(stringRequest)
    }
}