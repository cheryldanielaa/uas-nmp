package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize

//sementara nama tim distring dulu, klo dah digabungin baru diganti object
data class Achievement
    (
     var idachievement: Int,
     var namatim:String,
     var idgame:Int,
     var year:Int, //tampilin tahunnya ajah
     var namalomba:String,
     var description:String):Parcelable
{

}