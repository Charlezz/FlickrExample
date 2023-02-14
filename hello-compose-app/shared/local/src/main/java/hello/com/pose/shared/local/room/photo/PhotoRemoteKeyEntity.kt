package hello.com.pose.shared.local.room.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_key")
data class PhotoRemoteKeyEntity(
    @PrimaryKey
    val nextPage: Int,
)
