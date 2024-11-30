package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Proposal(
    var id: Int,
    var idUser: User,
    var idTeam: TeamBank,
    var desc: String,
    var status: String
): Parcelable
