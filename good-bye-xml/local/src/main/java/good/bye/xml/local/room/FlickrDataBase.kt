package good.bye.xml.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import good.bye.xml.local.room.dao.PhotoDao
import good.bye.xml.local.room.model.PhotoEntity
import good.bye.xml.local.room.model.PhotoRemoteKeyEntity

@Database(
    entities = [
        PhotoEntity::class,
        PhotoRemoteKeyEntity::class
    ],
    version = 1
)
abstract class FlickrDataBase: RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao

    companion object {
        private const val DATABASE_NAME = "flickr.db"

        private var instance: FlickrDataBase? = null

        fun getInstance(context: Context): FlickrDataBase {
            if (instance == null) {
                synchronized(FlickrDataBase::class) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            FlickrDataBase::class.java,
                            DATABASE_NAME
                        ).build()
                }
            }

            return instance!!
        }
    }
}