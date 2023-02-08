package kwon.dae.won.domain.model

import kotlin.math.abs

/**
 * @author soohwan.ok
 * @see <a href="https://www.flickr.com/services/api/misc.urls.html">이미지 불러 오는 방법</a>
 * <br>
 * 다음과 같은 형식으로 이미지를 불러옵니다. <br>
 * https://live.staticflickr.com/{server-id}/{id}_{secret}_{size-suffix}.jpg<br>
 * <br>
 * 사진 사이즈는 [size-suffix]에 의해 결정됩니다.<br>
 * <br>
 * 아무 것도 넣지 않으면 500px 이하로 이미지를 불러옵니다.<br>
 */
enum class PhotoSize(val px: Int, val sizeSuffix: String) {
    SIZE_75(75, "s"),
    SIZE_100(100, "t"),
    SIZE_150(150, "q"),


    SIZE_240(240, "m"),
    SIZE_320(320, "n"),
    SIZE_400(400, "w"),

    SIZE_650(640, "z"),
    SIZE_800(800, "c"),

    SIZE_1024(1024, "b"),
    SIZE_1600(1600, "h"),
    SIZE_2048(2048, "k"),

    SIZE_3K(3072, "3k"),
    SIZE_4K(4096, "4k"),
    SIZE_4K_V(4096, "f"), // 2:1 종횡비 사진에만 해당
    SIZE_5K(5120, "5k"),
    SIZE_6K(6144, "6k"),

    ORIGINAL(0, "o");

    companion object{
        fun getProperSize(width: Int, height: Int): PhotoSize {
            val longerSize = if(width > height) width else height

            var temp:Int
            var min = Int.MAX_VALUE
            var nearSize = ORIGINAL


            PhotoSize.values().forEach {size->
                temp = abs(longerSize - size.px)
                if(min > temp){
                    min = temp
                    nearSize = size
                }
            }

            if(SIZE_4K == nearSize && height*2 == width){
                nearSize = SIZE_4K_V
            }
            return nearSize
        }
    }
}