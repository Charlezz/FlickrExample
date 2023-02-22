package hello.com.pose.shared.local.room.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hello.com.pose.shared.data.photo.PhotoLocalDataSource
import hello.com.pose.shared.data.setting.SettingLocalDataSource
import hello.com.pose.shared.local.room.AppDatabase
import hello.com.pose.shared.local.room.photo.PhotoDao
import hello.com.pose.shared.local.room.photo.PhotoLocalDataSourceImpl
import hello.com.pose.shared.local.room.setting.SettingLocalDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class LocalDataBindsModule {
    @Binds
    abstract fun bindPhotoLocalDataSource(
        dataSource: PhotoLocalDataSourceImpl
    ): PhotoLocalDataSource

    @Binds
    abstract fun bindSettingLocalDataSource(
        dataSource: SettingLocalDataSourceImpl
    ): SettingLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        AppDatabase.buildDatabase(context)

    @Provides
    @Singleton
    fun providePhotoDao(appDatabase: AppDatabase): PhotoDao = appDatabase.getPhotoDao()
}
