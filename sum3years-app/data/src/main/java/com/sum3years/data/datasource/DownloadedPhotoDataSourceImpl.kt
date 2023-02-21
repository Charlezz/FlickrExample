package com.sum3years.data.datasource

import com.sum3years.data.database.dao.DownloadedPhotoDao
import com.sum3years.data.database.dao.SearchHistoryDao
import com.sum3years.data.model.DownloadedPhotoEntity
import com.sum3years.data.model.Photo
import com.sum3years.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DownloadedPhotoDataSourceImpl @Inject constructor(
    private val downloadedPhotoDao: DownloadedPhotoDao,
) : DownloadedPhotoDataSource {
    override fun loadDownloadedPhotos(): Flow<List<String>> {
        return downloadedPhotoDao.loadDownloadedPhoto().map { downloadedPhotos ->
            downloadedPhotos.map { downloadedPhoto -> downloadedPhoto.fileUri }
        }
    }

    override suspend fun deleteDownloadedPhoto(fileUri: String) {
        downloadedPhotoDao.deleteDownloadedPhoto(fileUri)
    }

    override suspend fun insertDownloadedPhoto(fileUri: String) {
        downloadedPhotoDao.insertDownloadedPhoto(
            DownloadedPhotoEntity(fileUri, System.currentTimeMillis()),
        )
    }
}
