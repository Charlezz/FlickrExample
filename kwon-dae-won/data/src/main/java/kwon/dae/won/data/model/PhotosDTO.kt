package kwon.dae.won.data.model

/**
 * @author soohwan.ok
 */
@JsonObjectKey(value = "photos")
data class PhotosDTO(
    val page:Int,
    val pages:Int,
    val perpage:Int,
    val total:Int,
    val photo:List<PhotoDTO>
)