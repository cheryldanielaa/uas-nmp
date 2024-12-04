package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamBank (var idteam:Int,
                     var idgame:Int,
                     var name:String
                     ):Parcelable {
    override fun toString(): String {
        return name
    }
}