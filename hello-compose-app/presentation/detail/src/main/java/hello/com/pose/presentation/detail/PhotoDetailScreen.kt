package hello.com.pose.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import hello.com.pose.ui.image.AsyncImage
import hello.com.pose.ui.system.component.IconBack

@Composable
fun PhotoDetailRoute(
    url: String,
    title: String,
    onClickBack: () -> Unit,
) {
    PhotoDetailScreen(url, title, onClickBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    model: Any,
    title: String,
    onClickBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SmallTopAppBar(
            title = { Text(title) },
            navigationIcon = { IconBack(onClickBack) }
        )
        ZoomableImage2(model = model)
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.doubleClickable(
    onDoubleClick: () -> Unit
): Modifier = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { },
        onDoubleClick = onDoubleClick
    )
}

@Composable
private fun ZoomableImage2(
    model: Any,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Color.Transparent
) {
    var scale by remember { mutableStateOf(MinScaleSize) }
    var offsetX by remember { mutableStateOf(1f) }
    var offsetY by remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor)
            .fillMaxSize()
            .doubleClickable {
                if (scale >= 2f) {
                    scale = MinScaleSize
                    offsetX = 1f
                    offsetY = 1f
                } else {
                    scale = MaxScaleSize
                }
            }
            .pointerInput(null) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale *= event.calculateZoom()
                            if (scale > 1) {
                                val offset = event.calculatePan()
                                offsetX += offset.x
                                offsetY += offset.y
                            } else {
                                scale = MinScaleSize
                                offsetX = 1f
                                offsetY = 1f
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
            }
    ) {
        AsyncImage(
            model = model,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    scaleX = maxOf(MinScaleSize, minOf(MaxScaleSize, scale))
                    scaleY = maxOf(MinScaleSize, minOf(MaxScaleSize, scale))
                    translationX = offsetX
                    translationY = offsetY
                },
            contentScale = ContentScale.FillWidth
        )
    }
}

internal const val MinScaleSize = 1f
internal const val MaxScaleSize = 3f