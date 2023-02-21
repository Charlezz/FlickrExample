package good.bye.xml.local.room.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import good.bye.xml.local.room.FlickrDataBase
import good.bye.xml.local.room.dao.PhotoDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideFlickrDataBase(@ApplicationContext context: Context): FlickrDataBase {
        return FlickrDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providePhotoDao(flickrDataBase: FlickrDataBase): PhotoDao {
        return flickrDataBase.getPhotoDao()
    }
}