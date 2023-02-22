package good.bye.xml.local.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import good.bye.xml.local.room.model.PhotoEntity
import good.bye.xml.local.room.model.PhotoRemoteKeyEntity

@Dao
interface PhotoDao {

    // Photo 테이블 목록 다중 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    // Photo 테이블 전체 삭제
    @Query("DELETE FROM photo")
    suspend fun deleteAll()

    // Photo 테이블 페이징소스 반환
    @Query("SELECT * FROM photo")
    fun pagingSource(): PagingSource<Int, PhotoEntity>

    // Paging Key 교체
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replacePagingRemoteKey(remoteKey: PhotoRemoteKeyEntity)

    // Pagin Key 반환depen
    @Query("SELECT * FROM photo_remote_key LIMIT 1")
    suspend fun getPagingKey(): PhotoRemoteKeyEntity

    // Paging Key 삭제
    @Query("DELETE FROM photo_remote_key")
    suspend fun clearPagingKey()

}