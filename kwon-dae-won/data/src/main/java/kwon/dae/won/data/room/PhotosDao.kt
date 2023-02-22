package kwon.dae.won.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kwon.dae.won.data.model.PhotoDTO
import kwon.dae.won.domain.model.Photo

/**
 * @author Daewon on 09,February,2023
 *
 */
@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<PhotoDTO>)

    @Query("SELECT * FROM photos ORDER BY id DESC")
    fun getPhotos(): PagingSource<Int, Photo>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getPhotosById(id: String): List<Photo>

    @Query("DELETE FROM photos")
    suspend fun clearAllPhotos()

    @Query("Select created_at From photos Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
