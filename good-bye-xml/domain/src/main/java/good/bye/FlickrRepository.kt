package good.bye

interface FlickrRepository {

    fun getPhotos(): Unit

    fun downloadPhotos(): Unit
}
