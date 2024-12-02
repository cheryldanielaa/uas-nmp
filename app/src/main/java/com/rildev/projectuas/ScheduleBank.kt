package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleBank (var id: Int, var tanggalEvent:String,
                         var waktuEvent:String, var namaEvent:String, var tempatEvent:String,
                         var cabangLomba: Cabang, var namaTeam: TeamBank, var deskripsi:String,
                         var gambar:String) : Parcelable