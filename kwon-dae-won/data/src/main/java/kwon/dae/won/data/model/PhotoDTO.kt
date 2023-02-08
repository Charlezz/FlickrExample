package kwon.dae.won.data.model

/**
 * @author soohwan.ok
 */
data class PhotoDTO(
    val id:String,
    val owner:String,
    val secret:String,
    val server:String,
    val farm:Int,
    val title:String,
    val ispublic:Int,
    val isfriend:Int,
    val isfamily:Int,
)