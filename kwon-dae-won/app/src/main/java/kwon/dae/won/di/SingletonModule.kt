package kwon.dae.won.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author soohwan.ok
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {

    @Binds
    abstract fun bindsContext(application: Application): Context
}