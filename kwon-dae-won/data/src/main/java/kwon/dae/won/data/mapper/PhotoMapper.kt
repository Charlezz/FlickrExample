package kwon.dae.won.data.mapper

import kwon.dae.won.data.model.PhotoDTO
import kwon.dae.won.domain.model.Photo

/**
 * @author soohwan.ok
 */
object PhotoMapper {
    fun from(dto: PhotoDTO): Photo {
        return Photo(
            id = dto.id,
            owner = dto.owner,
            secret = dto.secret,
            title = dto.title,
            page = dto.page,
            maxPage = dto.maxPage,
        )
    }
}
