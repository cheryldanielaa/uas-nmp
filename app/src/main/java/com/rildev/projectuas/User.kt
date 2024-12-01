package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var idmember:Int,
    var fname:String,
    var lname:String,
    var username:String,
    var password:String,
    var profile:String="member"):Parcelable //selalu member tdk ada role admin