package hello.com.pose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hello.com.pose.Downloader

@[Module InstallIn(SingletonComponent::class)]
object MainModule {
    @Provides
    fun provideDownloader(@ApplicationContext context: Context): Downloader = Downloader(context)
}