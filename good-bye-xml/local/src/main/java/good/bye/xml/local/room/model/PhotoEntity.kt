package good.bye.xml.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val index: Int = 0,

    val id: Long,

    val owner: String,

    val secret: String,

    val server: Int,

    val farm: Int,

    val title: String,

    @ColumnInfo(name = "ispublic")
    val isPublic: Int,

    @ColumnInfo(name = "isfriend")
    val isFriend: Int,

    @ColumnInfo(name = "isfamily")
    val isFamily: Int
)
