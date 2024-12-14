package com.rildev.projectuas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityAchievementDetailsBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class AchievementDetails : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementDetailsBinding
    companion object
    {
        var CABANG_ACHIEVEMENT = "pencapaian cabang"
    }
    private var  achievement:ArrayList<Achievement> = ArrayList()
    lateinit var selectedGame:Cabang //utk nampung cabang yg dipilih user
    private var yearlyachievement:ArrayList<Achievement> = ArrayList() //tampung achievement per tahun
    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityAchievementDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //retrieve value dari object yang dikirimkan dari what we play yang dipilih
        achievement = intent.getParcelableArrayListExtra<Achievement>(CABANG_ACHIEVEMENT) ?: ArrayList()

        //lakuin volley buat ambil bg cabang gamenya
        //sekarang panggil volley buat ngeset gambar biar sesuai hehe
        val idGame = achievement[0].idgame //ambil idgame krn pasti sama jd perwakilan index 0 aja

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
                    Picasso.get().load(urlGambar).into(binding.gambarLogo) //diload dimana

                    binding.txtNama.text = selectedGame.name
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

        //buat sumber data utk dropdown
        // Ambil tahun yang unik dan urutkan secara ascending
        val years = achievement.map { it.year } //it.year >> ambil komponen year dr array achievement
            .distinct()  // Hanya ambil yang unik
            .sorted()    // Urutkan secara ascending

        // Tambahkan "All" di bagian atas
        val yearsWithAll = listOf("All") + years.map { it.toString() } //val yearsnya smua diubah jd tostring
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            yearsWithAll
        )
        //pasang adapter ke spinner
        binding.spinnerYear.adapter = adapter

        //atur query tiap kali ubah yg diklik
        binding.spinnerYear.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            )
            {
                //buat ambil taun yang dipilih yang mana
                val selectedYear = parent.getItemAtPosition(position).toString()
                Log.d("year",selectedYear.toString())
                if(selectedYear!="All"){

                    //harus pake @achievement details krn menunjukkan thisnya siapa
                    val q = Volley.newRequestQueue(this@AchievementDetails)

                    //masukin link ubaya xyz
                    val url = "https://ubaya.xyz/native/160422026/project/getachievementyear.php"
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
                                val sType = object : TypeToken<ArrayList<Achievement>>() {}.type
                                yearlyachievement = Gson().fromJson(data.toString(), sType)
                                //logcat sgt berjaya <333
                                Log.d("yearachievement",yearlyachievement.toString())

                                //isiin di textbox >> loop
                                var i = 0; //utk condition udh brp data
                                var text = ""
                                for (ach in yearlyachievement)
                                {
                                    i+=1
                                    var namatim = ach.namatim
                                    var desc = ach.description
                                    //klo mau masukin value pke $
                                    //jgn lupa dienter stlh selesai
                                    text+= "$i. $desc ($selectedYear) - $namatim\n"
                                }
                                binding.txtAchievement.text = text
                            }
                        },
                        Response.ErrorListener {
                            Log.d("apiresult", it.message.toString())
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["id"] = selectedGame.idgame.toString() //kirim idgame
                            params["year"]= selectedYear.toString() //tahun yang dipilih
                            return params
                        }
                    }
                    q.add(stringRequest)
                }
                else {
                    //isiin di textbox >> loop
                    var i = 0; //utk condition udh brp data
                    var text = ""
                    for (ach in achievement) //achievement secara umum
                    {
                        i+=1
                        var namatim = ach.namatim
                        var desc = ach.description
                        var tahun = ach.year.toString()
                        //klo mau masukin value pke $
                        text+= "$i. $desc ($tahun) - $namatim\n"
                    }
                    binding.txtAchievement.text = text
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }
}