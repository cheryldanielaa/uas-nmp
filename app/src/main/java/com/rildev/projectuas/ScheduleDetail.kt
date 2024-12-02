package com.rildev.projectuas

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ScheduleDetail : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleDetailBinding

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

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                schedule = Gson().fromJson(it, ScheduleBank::class.java)

                // Format the event date
                val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                val tanggalDate: Date? = inputFormat.parse(schedule.tanggalEvent)
                val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                val tanggalPanjang = tanggalDate?.let { format.format(it) } ?: schedule.tanggalEvent

                binding.txtNamaEvent.text = schedule.namaEvent
                binding.txtTanggalEvent.text = "$tanggalPanjang - ${schedule.waktuEvent}"
                binding.txtTempatEvent.text = schedule.tempatEvent
                binding.txtNamaCabang.text = schedule.cabangLomba.namaCabang
                binding.txtNamaTeam.text = schedule.namaTeam.nama
                binding.txtDeskripsiEvent.text = schedule.deskripsi

                val imageUrl = schedule.gambar
                val builder = Picasso.Builder(this)
                builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                Picasso.get().load(imageUrl).into(binding.imgEvent)

                // Hide the notify button if the event date has passed
                if (isDateBeforeToday(tanggalDate)) {
                    binding.btnNotify.visibility = View.GONE
                }

                // Set notification button functionality
                binding.btnNotify.setOnClickListener {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Notification")
                    builder.setMessage("Yey, masuk notifikasinya! ^.^")
                    builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    val dialog = builder.create()
                    dialog.setOnShowListener {
                        dialog.window?.decorView?.setBackgroundColor(Color.parseColor("#F0B5C9"))
                    }
                    dialog.show()
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
