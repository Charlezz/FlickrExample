package good.bye.xml.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_key")
data class PhotoRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val index: Int = 1,
    val page: Int,
    @ColumnInfo(name = "pages")
    val totalPages: Int
) {
    val canMorePaging: Boolean
        get() = page <= totalPages
}