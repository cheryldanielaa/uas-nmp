package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleBank (var tanggalEvent:String,
                         var waktuEvent:String, var namaEvent:String, var tempatEvent:String,
                         var cabangLomba: Cabang, var tim: TeamBank, var deskripsi:String,
                         var gambarId:Int) : Parcelable