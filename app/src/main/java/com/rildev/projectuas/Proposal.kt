package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Proposal(
    var namaCabang: String,
    var status: String
): Parcelable
