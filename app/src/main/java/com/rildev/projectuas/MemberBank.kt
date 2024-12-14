package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberBank (var idteam:Int,
                       var idmember:Int,
                       var idgame:Int,
                       var role:String,
                       var nickname: String,
                       var gambarmember:String,
                       var teamname:String) :Parcelable   {
}