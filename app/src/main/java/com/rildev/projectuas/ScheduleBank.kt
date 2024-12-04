package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleBank (var eventId: Int,
                         var tanggalEvent:String,
                         var waktuEvent:String,
                         var namaEvent:String,
                         var tempatEvent:String,
                         var cabangId: Int,
                         var namaCabang:String,
                         var teamName: String,
                         var cabangDesc:String,
                         var teamId:Int,
                         var deskripsiLomba:String,
                         var gambarLomba:String,
                         var logoGambar:String) : Parcelable