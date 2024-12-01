package com.rildev.projectuas

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rildev.projectuas.databinding.ActivityScheduleDetailBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ScheduleDetail : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleDetailBinding
    companion object
    {
        var SCHEDULE = "schedule detail"
        var POSITION = "posisi"
    }
    private var schedule:ArrayList<ScheduleBank> = ArrayList()
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //byebye night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve value dari object yang dikirimkan dari what we play yang dipilih
        schedule =
            intent.getParcelableArrayListExtra<ScheduleBank>(SCHEDULE) ?: ArrayList()
        position = intent.getIntExtra(POSITION, 0)

        var tanggalEvent = schedule[position].tanggalEvent
        var waktuEvent = schedule[position].waktuEvent
        var namaEvent = schedule[position].namaEvent
        var tempatEvent = schedule[position].tempatEvent
        var cabangLomba = schedule[position].cabangLomba.namaCabang
        var namaTim = schedule[position].tim.nama
        var deskripsi = schedule[position].deskripsi
        var gambar = schedule[position].gambarId

        //format 16-10-2024 menjadi 16 October 2024
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var tanggalDate: Date? = null
        try {
            tanggalDate = inputFormat.parse(tanggalEvent)  //pastiin parsing berhasil
        } catch (e: Exception) {
            e.printStackTrace() //tangani parsing gagal
        }

        val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val tanggalPanjang = format.format(tanggalDate)

        binding.txtNamaEvent.text = namaEvent
        binding.txtTanggalEvent.text = tanggalPanjang + " - " + waktuEvent
        binding.txtTempatEvent.text = tempatEvent
        binding.txtNamaCabang.text = cabangLomba
        binding.txtNamaTeam.text = namaTim
        binding.txtDeskripsiEvent.text = deskripsi
        binding.imgEvent.setImageResource(gambar)

        //reset komponen waktu pada Calendar menjadi 00:00:009
        fun resetTime(calendar: Calendar) {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

        //bandingin tanggal, tanpa waktu (jam, menit, detik, dll) biar tanggal hari ini juga ikut
        fun isDateBeforeToday(eventDate: Date?): Boolean {
            val calendarEvent = Calendar.getInstance()
            calendarEvent.time = eventDate
            resetTime(calendarEvent)

            val today = Calendar.getInstance()
            resetTime(today)

            return calendarEvent.before(today)
        }

        if(isDateBeforeToday(tanggalDate)) {
            binding.btnNotify.visibility = View.GONE
        }

        binding.btnNotify.setOnClickListener {
            //buat dan tampilkan AlertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Notification")
            builder.setMessage("yey udah masuk notifnya ^.^")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() //tutup dialog saat OK ditekan
            }

            //ganti warna dialog jadi pink
            val dialog = builder.create()
            dialog.setOnShowListener {
                dialog.window?.decorView?.setBackgroundColor(Color.parseColor("#F0B5C9"))
            }
            dialog.show()
        }
    }
}