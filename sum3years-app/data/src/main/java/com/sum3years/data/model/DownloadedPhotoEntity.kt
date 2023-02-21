package com.sum3years.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DownloadedPhotoTable")
data class DownloadedPhotoEntity(
    @PrimaryKey val fileUri: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
