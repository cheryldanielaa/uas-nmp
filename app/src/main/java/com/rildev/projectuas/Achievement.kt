package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize

//sementara nama tim distring dulu, klo dah digabungin baru diganti object
data class Achievement (var cabangLomba:Cabang, var year:Int,
                            var desc:String, var tim:TeamBank):Parcelable
{
    override fun toString(): String {
        return year.toString() //return year buat ditampilin di dropdown
    }
}