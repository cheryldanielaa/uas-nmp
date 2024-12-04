package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize //@xxx itu namanya annotation krn kita nambahin plugin hehe
//tujuan parcelable itu nanti object diubah jadi bytestream, yang bisa dikirimkan
//hasil bytestream yang diterima itu nanti bisa diubah lagi jadi object

//buat simpen constructor dan data members dari cabang HEHE
data class Cabang (
    var idgame:Int,
    var name:String,
    var description:String,
    var gambar:String): Parcelable {
    override fun toString()=name //yang direturn apa aja{
}