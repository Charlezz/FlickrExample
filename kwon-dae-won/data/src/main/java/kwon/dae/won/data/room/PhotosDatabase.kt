package kwon.dae.won.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kwon.dae.won.data.model.PhotoDTO
import kwon.dae.won.data.model.RemoteKeys

/**
 * @author Daewon on 09,February,2023
 *
 */
@Database(
    entities = [PhotoDTO::class, RemoteKeys::class],
    version = 1,
)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): PhotosDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
