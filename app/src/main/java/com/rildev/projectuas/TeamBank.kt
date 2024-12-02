package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamBank (var id: Int, var nama:String,
                     var cabang:Cabang):Parcelable {
    override fun toString(): String {
        return nama
    }
}