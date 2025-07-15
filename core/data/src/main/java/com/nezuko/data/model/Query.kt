package com.nezuko.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Query(
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = ""
) : Parcelable

