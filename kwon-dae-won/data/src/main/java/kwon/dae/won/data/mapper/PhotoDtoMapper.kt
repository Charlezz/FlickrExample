package kwon.dae.won.data.mapper

import kwon.dae.won.data.model.PhotoDTO
import kwon.dae.won.domain.model.Photo

/**
 * @author soohwan.ok
 */
object PhotoDtoMapper {
    fun from(photo: Photo): PhotoDTO {
        return PhotoDTO(
            id = photo.id,
            owner = photo.owner,
            secret = photo.secret,
            server = photo.server,
            farm = photo.farm,
            title = photo.title,
            ispublic = photo.ispublic,
            isfriend = photo.isfriend,
            isfamily = photo.isfamily,
        )
    }
}
