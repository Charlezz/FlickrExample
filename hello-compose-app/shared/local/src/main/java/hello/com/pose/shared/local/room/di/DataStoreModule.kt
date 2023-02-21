package hello.com.pose.shared.local.room.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesSettingPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.settingDataStore
}


val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")