package hello.com.pose.shared.remote.di

import com.assignment.cm.data.banner.BannerRemoteDataSource
import com.assignment.cm.data.feed.FeedRemoteDataSource
import com.assignment.cm.data.product.ProductRemoteDataSource
import com.assignment.cm.remote.banner.DefaultBannerRemoteDataSource
import com.assignment.cm.remote.feed.DefaultFeedRemoteDataSource
import com.assignment.cm.remote.product.DefaultProductRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
internal abstract class RemoteDataBindsModule {

    @Binds
    abstract fun bindBannerRemoteDataSource(
        dataSource: DefaultBannerRemoteDataSource
    ): BannerRemoteDataSource

    @Binds
    abstract fun bindProductRemoteDataSource(
        dataSource: DefaultProductRemoteDataSource
    ): ProductRemoteDataSource

    @Binds
    abstract fun bindFeedRemoteDataSource(
        dataSource: DefaultFeedRemoteDataSource
    ): FeedRemoteDataSource
}