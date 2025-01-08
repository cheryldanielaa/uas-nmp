package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityAchievementDetailsBinding
import com.rildev.projectuas.databinding.FragmentWhatWePlayBinding
import com.rildev.projectuas.databinding.ActivityTeamPageListBinding
import org.json.JSONObject
import com.android.volley.Request
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class TeamPageList : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPageListBinding
    private var tim:ArrayList<TeamBank> = ArrayList()
    lateinit var selectedGame:Cabang //utk nampung cabang yg dipilih user
    companion object
    {
        //kirim value object
        var DAFTARTIM="halo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityTeamPageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //parameter buat dipake di TeamAdapter
        tim = intent.getParcelableArrayListExtra<TeamBank>(DAFTARTIM)?: ArrayList()
        val teamAdapter = TeamAdapter(tim)
        binding.recTeam.adapter = teamAdapter
        binding.recTeam.layoutManager = LinearLayoutManager(this)
        binding.recTeam.setHasFixedSize(true)

        //sekarang panggil volley buat ngeset gambar biar sesuai hehe
        val idGame = tim[0].idgame //ambil idgame krn pasti sama jd perwakilan index 0 aja

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

                    //klo berhasil maka set bindingnya sesuai url selected game hehe
                    val urlGambar = selectedGame.gambar; //url gambar
                    //gunakan picasso untuk nampilin gambar
                    val builder = Picasso.Builder(this) //pake this krn di activity
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(urlGambar).into(binding.imgCabang) //diload dimana
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