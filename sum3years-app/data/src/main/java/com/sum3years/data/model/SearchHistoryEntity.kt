package com.sum3years.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchHistoryTable")
data class SearchHistoryEntity(
    @PrimaryKey val history: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
