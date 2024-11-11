package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberBank (var nickame:String,
                       var avatarId:Int,
                       var role:String,
                       var teamName:TeamBank) :Parcelable   {
}