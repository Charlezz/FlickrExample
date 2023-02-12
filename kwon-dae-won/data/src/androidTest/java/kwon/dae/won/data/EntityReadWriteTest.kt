package kwon.dae.won.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kwon.dae.won.data.model.PhotoDTO
import kwon.dae.won.data.room.PhotosDao
import kwon.dae.won.data.room.PhotosDatabase
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * @author Daewon on 10,February,2023
 */
@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {
    private lateinit var photosDao: PhotosDao
    private lateinit var db: PhotosDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PhotosDatabase::class.java).build()
        photosDao = db.getPhotosDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun writePhotoAndReadInList() = runBlocking {
        val photoList = listOf(
            PhotoDTO("1","","","",0,"",0,0,0),
            PhotoDTO("2","","","",0,"",0,0,0),
            PhotoDTO("3","","","",0,"",0,0,0)
        )
        photosDao.insertAll(photoList)
        val byId = photosDao.getPhotosById("2")
        assertTrue(byId[0].id == photoList.find { it.id == "2" }?.id)
    }
}
