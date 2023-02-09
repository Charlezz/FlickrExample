package kwon.dae.won.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kwon.dae.won.data.room.PhotosDao
import kwon.dae.won.data.room.PhotosDatabase
import kwon.dae.won.data.room.RemoteKeysDao
import javax.inject.Singleton

/**
 * @author Daewon on 09,February,2023
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providePhotoDatabase(@ApplicationContext context: Context): PhotosDatabase =
        Room.databaseBuilder(context, PhotosDatabase::class.java, "photos_database").build()

    @Singleton
    @Provides
    fun providePhotosDao(photosDatabase: PhotosDatabase): PhotosDao = photosDatabase.getPhotosDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(photosDatabase: PhotosDatabase): RemoteKeysDao = photosDatabase.getRemoteKeysDao()
}
