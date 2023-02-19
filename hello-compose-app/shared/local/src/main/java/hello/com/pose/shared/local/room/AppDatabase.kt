package hello.com.pose.shared.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hello.com.pose.shared.data.photo.PhotoEntity
import hello.com.pose.shared.local.room.photo.PhotoDao
import hello.com.pose.shared.local.room.photo.PhotoRemoteKeyEntity

@Database(
    entities = [
        PhotoEntity::class,
        PhotoRemoteKeyEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDao

    companion object {
        private const val DATABASE_NAME = "room-persistent-db"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}
