package com.rildev.projectuas

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.google.android.material.R
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.color.MaterialColors
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ScheduleDetail : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleDetailBinding

    //buat terima intent schedule ID dari OurScheduleAdapter
    companion object {
        const val SCHEDULE_ID = "schedule_id"
    }

    private var scheduleId: Int = 0
    private lateinit var schedule: ScheduleBank

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduleId = intent.getIntExtra(SCHEDULE_ID, 0)
        fetchScheduleDetails()
    }

    private fun fetchScheduleDetails() {
        val url = "https://ubaya.xyz/native/160422026/project/scheduledetail.php"
        val q = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)

                //check for result key. If value is OK, then proceed
                //cara panggil obj pake getString
                if (obj.getString("result") == "OK") {
                    //data --> ini ambil structur di JSON yaitu []data
                    //karena JSON bentuknya object, sedangkan data bentuknya array, maka convert object jadi array
                    val data = obj.getJSONArray("data")
                    val schedJson = data.getJSONObject(0)

                    //untuk mendapatkan object user yang lagi login
                    //dia TypeTokennya ga array karna cmn ada 1 objek (bikos sudah di filter pake query & outputnya cmn 1 objek)
                    val sType = object : TypeToken<ScheduleBank>() {}.type

                    //parameter ke 2 -> specifies the target class into which Gson should map the JSON data.
                    //Gson will map each key in the JSON to the corresponding property in the ScheduleBank class.
                    //thankyou gson luv <3
                    schedule = Gson().fromJson(schedJson.toString(), sType)

                    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                    val tanggalDate: Date? = inputFormat.parse(schedule.tanggalEvent)
                    val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                    val tanggalPanjang = tanggalDate?.let { format.format(it) } ?: schedule.tanggalEvent

                    binding.txtNamaEvent.text = schedule.namaEvent
                    binding.txtTanggalEvent.text = "$tanggalPanjang - ${schedule.waktuEvent}"
                    binding.txtTempatEvent.text = schedule.tempatEvent
                    binding.txtNamaCabang.text = schedule.namaCabang
                    binding.txtNamaTeam.text = schedule.teamName
                    binding.txtDeskripsiEvent.text = schedule.deskripsiLomba

                    val imageUrl = schedule.gambarLomba
                    val builder = Picasso.Builder(this)
                    builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                    Picasso.get().load(imageUrl).into(binding.imgEvent)

                    if (isDateBeforeToday(tanggalDate)) {
                        binding.btnNotify.visibility = View.GONE
                    }

                    binding.btnNotify.setOnClickListener {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Notification")
                        builder.setMessage("Yey, masuk notifikasinya! ^.^")
                        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        val dialog = builder.create()
                        dialog.setOnShowListener {
                            //sesuaiin warna notif sama material design
                            val backgroundColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimaryContainer, Color.WHITE)
                            dialog.window?.decorView?.setBackgroundColor(backgroundColor)

                        }
                        dialog.show()
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = scheduleId.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    //dia set komponen time di Calendar eventDate & today jadi zero semua biar waktu compare itu solely based on tanggal doang
    private fun isDateBeforeToday(eventDate: Date?): Boolean {
        val calendarEvent = Calendar.getInstance()
        eventDate?.let { calendarEvent.time = it }
        resetTime(calendarEvent)

        val today = Calendar.getInstance()
        resetTime(today)

        return calendarEvent.before(today)
    }

    private fun resetTime(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }
}
