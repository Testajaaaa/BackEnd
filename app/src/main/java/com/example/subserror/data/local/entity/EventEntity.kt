package com.example.subserror.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "event_table")
@Parcelize
data class EventEntity (
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var mediaCover: String? = null,
    var imageLogo: String? = null,
    var description: String? = null,
    val ownerName: String? = null,
    var cityName: String? = null,
    var quota: Int? = null,
    var registrants: Int? = null,
    var link: String? = null,
    var beginTime: String? = null,
    var isBookmarked: Boolean,
    var isActive: Boolean
) : Parcelable
