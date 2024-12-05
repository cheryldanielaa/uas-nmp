package com.rildev.projectuas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUs(var id: Int, var name: String, var description: String, var photo: String, var num_likes: Int): Parcelable
