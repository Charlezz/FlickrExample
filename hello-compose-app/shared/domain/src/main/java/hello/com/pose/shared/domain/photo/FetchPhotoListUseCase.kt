package hello.com.pose.shared.domain.photo

import javax.inject.Inject

class FetchPhotoListUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(query: String, page: Int): List<Photo> {
        return photoRepository.fetchSearchPhoto(query, page)
    }
}