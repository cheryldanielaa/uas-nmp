package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var first_name:String,
    var last_name:String,
    var username:String,
    var password:String): Parcelable