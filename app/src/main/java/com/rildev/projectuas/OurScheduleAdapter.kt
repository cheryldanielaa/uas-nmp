package com.rildev.projectuas

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rildev.projectuas.CabangAdapter.CabangViewHolder
import com.rildev.projectuas.databinding.CabangListBinding
import com.rildev.projectuas.databinding.SchedulePageCardBinding
import java.text.SimpleDateFormat
import java.util.*

class OurScheduleAdapter(private val schedule: ArrayList<ScheduleBank>) : RecyclerView.Adapter<OurScheduleAdapter.ScheduleViewHolder>(){
    //view holder kelasnya dibuat di dalam
    //cmn bs digunakan di dlm adapter
    //view holder extend recycle >> dimana recycle itu parentnya
    //artinya view pny akses ke UI, dmn cabang list binding itu file xml cardnya

    //pake listOf soalnya mau disort, karena ArrayList itu gabisa diedit, jadi diganti dlu
    private var scheduleValid = listOf<ScheduleBank>()

    //init ini kayak onCreate jadi dia dijalankan waktu Adapter pertama kali dibuat, dia function yang dijalankan paling pertama
    init {
        //urutkan list ascending tanggal event
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        scheduleValid = schedule.sortedBy { schedule ->
            inputFormat.parse(schedule.tanggalEvent)
        }
//        }.filter {
//            //cuma simpan event yg tanggalnya belum lewat
//            val eventDate = inputFormat.parse(it.tanggalEvent)
//            eventDate != null && !isDateBeforeToday(eventDate)
//        }
    }

//    private fun isDateBeforeToday(eventDate: Date): Boolean {
//        val calendarEvent = Calendar.getInstance()
//        calendarEvent.time = eventDate
//        resetTime(calendarEvent)
//
//        val today = Calendar.getInstance()
//        resetTime(today)
//
//        return calendarEvent.before(today)
//    }

//    private fun resetTime(calendar: Calendar) {
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
//        calendar.set(Calendar.MINUTE, 0)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//    }

    class ScheduleViewHolder(val binding: SchedulePageCardBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        //digunakan utk membuat object ScheduleViewHolder
        val binding = SchedulePageCardBinding.inflate(
            LayoutInflater.
            from(parent.context),parent,false)
        return ScheduleViewHolder(binding) //ni buat cardnya
    }

    override fun getItemCount(): Int {
        return scheduleValid.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        //menghubungkan layout dgn data
        //fungsi ini dipanggil sebanyak item yg ingin ditampilkan
        //poisition itu mksdnya indexnya brp (berdasarkan array)
        //nama custom layout cardview diwakilkan oleh holder
        //holder itu di scheduleviewholder >> isi elemen di card

        val tanggal = scheduleValid[position].tanggalEvent

        //parse tanggal dari String ke Date
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var tanggalDate: Date? = null
        try {
            tanggalDate = inputFormat.parse(tanggal)  //pastiin parsing berhasil
        } catch (e: Exception) {
            e.printStackTrace() //tangani parsing gagal
        }

        //format 16-10-2024 menjadi 16 OCT 2024
        val format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val tanggalPanjang = format.format(tanggalDate)
        val dateParts = tanggalPanjang.split(" ")
        val tanggalEvent = dateParts[0]
        val bulanEvent = dateParts[1].uppercase()

        val cabang = scheduleValid[position].namaCabang
        val tim = scheduleValid[position].teamName

        holder.binding.txtCabang.text = scheduleValid[position].namaEvent
        holder.binding.txtTanggalEvent.text = tanggalEvent
        holder.binding.txtBulanEvent.text = bulanEvent
        holder.binding.txtStatus.text = cabang + " - " + tim

        holder.binding.cardSchedule.setOnClickListener {
            val activity = holder.itemView.context as Activity
            //parse balik scheduleValid ke List
            var scheduleValid = scheduleValid.toMutableList()
            val intent = Intent(activity, ScheduleDetail::class.java)
            intent.putExtra(ScheduleDetail.SCHEDULE_ID, scheduleValid[position].eventId)
            activity.startActivity(intent)
        }
    }
}