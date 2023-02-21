package good.bye.xml.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_key")
data class PhotoRemoteKeyEntity(
    @PrimaryKey
    val page: Int
)