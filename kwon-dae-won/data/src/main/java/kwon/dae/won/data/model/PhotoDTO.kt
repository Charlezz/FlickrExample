package kwon.dae.won.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author soohwan.ok
 */
@Entity(tableName = "photos")
data class PhotoDTO(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val owner: String,
    val secret: String,
    val title: String,
    val page: Int,
    val maxPage: Int,
    @ColumnInfo(name = "created_at")
    val createAt: Long = System.currentTimeMillis()
)
