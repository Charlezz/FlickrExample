package kwon.dae.won.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kwon.dae.won.data.model.RemoteKeys

/**
 * @author Daewon on 09,February,2023
 *
 */
@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key Where photo_id = :id")
    suspend fun getRemoteKeyByPhotoId(id: String): RemoteKeys?

    @Query("DELETE FROM remote_key")
    suspend fun clearAllPhotos()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
