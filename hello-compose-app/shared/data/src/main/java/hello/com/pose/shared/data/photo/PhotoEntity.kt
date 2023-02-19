package hello.com.pose.shared.data.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val server: String,
    val secret: String,
)
