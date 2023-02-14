package hello.com.pose.shared.local.room.photo

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hello.com.pose.shared.data.photo.PhotoEntity

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photo")
    fun getPhotoPagingSource(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM photo")
    suspend fun clearPhoto()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoRemoteKey(remoteKey: PhotoRemoteKeyEntity)

    @Query("SELECT * FROM photo_remote_key LIMIT 1")
    suspend fun getRemoteKey(): PhotoRemoteKeyEntity

    @Query("DELETE FROM photo_remote_key")
    suspend fun clearPhotoRemoteKey()

    @Transaction
    suspend fun replace(photos: List<PhotoEntity>, page: Int) {
        clearPhoto()
        clearPhotoRemoteKey()
        insertAllPhotos(photos)
        clearPhotoRemoteKey()
        insertPhotoRemoteKey(PhotoRemoteKeyEntity(page))
    }

    @Transaction
    suspend fun insertPhotoPage(photos: List<PhotoEntity>, nextPage: Int) {
        insertAllPhotos(photos)
        clearPhotoRemoteKey()
        insertPhotoRemoteKey(PhotoRemoteKeyEntity(nextPage))
    }
}
