package com.example.moodbloom.presentation.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.moodbloom.R
import com.example.moodbloom.ui.theme.AppTheme

/**
 * @author: Ihsaan Rashid
 * A Composable Base Function for Display Images.
 *
 * @param image image Resource ID or URL.
 * @param colorFilter The Tint Color to applied on the image.
 * @param modifier Modifier is used for customizing the layout.
 * @param alpha is used for fading the image 0.0 to 1.0.
 * @param alignment alignment is used for aligning the image within its bounds.
 * @param contentScale contentScale is used for scaling the image..
 */

@Composable
fun <T> ResourceImage(
    modifier: Modifier = Modifier,
    image: T? = null,
    colorFilter: ColorFilter? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    placeHolder: Int = R.mipmap.ic_launcher,
    contentScale: ContentScale = ContentScale.Fit,
) {
    when (image) {
        is Int -> Image(
            painter = painterResource(id = image),
            colorFilter = colorFilter,
            contentDescription = null,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
        )
        is String -> {
            if (image.isNotEmpty()) {
                if (image.endsWith(".svg", ignoreCase = true)) {
                    Image(
                        painter = rememberAsyncSvgImagePainterWithPlaceholder(
                            model = image,
                            placeholder = placeHolder,
                            error = placeHolder
                        ),
                        colorFilter = colorFilter,
                        contentDescription = null,
                        contentScale = contentScale,
                        modifier = modifier,
                        alignment = alignment,
                        alpha = alpha
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainterWithPlaceholder(
                            model = image,
                            placeholder = painterResource(placeHolder),
                            error = painterResource(placeHolder)
                        ),
                        colorFilter = colorFilter,
                        contentDescription = null,
                        contentScale = contentScale,
                        modifier = modifier,
                        alignment = alignment,
                        alpha = alpha
                    )
                }


            } else {
                Image(
                    painter = painterResource(id = placeHolder),
                    colorFilter = colorFilter,
                    contentDescription = null,
                    modifier = modifier,
                    alignment = alignment,
                    contentScale = contentScale,
                    alpha = alpha,
                )
            }
        }

        is Bitmap -> Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            colorFilter = colorFilter,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha
        )

        is Uri -> {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                colorFilter = colorFilter,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha
            )
        }

        is LottieCompositionSpec.RawRes -> AnimatedPreloader(image, modifier = modifier)
    }
}

/**
 * @author: Muhammad Kamran
 * A Composable Base Function for Display Images.
 *
 * @param icon image Resource ID or URL.
 * @param tint The Tint Color to applied on the image.
 * @param modifier Modifier is used for customizing the layout.
 */
@Composable
internal fun <T> ResourceIcon(
    modifier: Modifier = Modifier,
    icon: T,
    tint: Color = MaterialTheme.colorScheme.tertiary,
    placeHolder: Int = R.mipmap.ic_launcher,
) {
    when (icon) {
        is Int -> Icon(
            painter = painterResource(id = icon),
            tint = tint,
            contentDescription = null,
            modifier = modifier,
        )

        is String ->
            if (icon.endsWith(".svg", ignoreCase = true)) {
                Icon(
                    painter = rememberAsyncSvgImagePainterWithPlaceholder(
                        model = icon,
                        placeholder = placeHolder,
                        error = placeHolder
                    ),
                    tint = tint, contentDescription = null, modifier = modifier
                )
            } else {
                Icon(
                    painter = rememberAsyncImagePainterWithPlaceholder(
                        model = icon,
                        placeholder = painterResource(placeHolder),
                        error = painterResource(placeHolder)
                    ), tint = tint, contentDescription = null, modifier = modifier
                )
            }

        is Bitmap -> Icon(
            painter = rememberAsyncImagePainter(icon),
            contentDescription = null,
            tint = tint,
            modifier = modifier,
        )

        is LottieCompositionSpec.RawRes -> AnimatedPreloader(icon, modifier = modifier)
    }
}


@Composable
internal fun AnimatedPreloader(rawRes: LottieCompositionSpec.RawRes, modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        rawRes
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition, iterations = LottieConstants.IterateForever, isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition, progress = preloaderProgress, modifier = modifier
    )
}

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier, image: String, colorFilter: ColorFilter? = null
) {
    ResourceImage(image = image, modifier = modifier, colorFilter = colorFilter)
}

/**
 *
 * an overloaded function of the Network Image for displaying icons from URLs
 *
 * @param imageUrl Url of the image you wish to fetch from the internet
 * @param placeholder a composable object for when an object is clicked and is in the loading stage
 * @param error in case of an error fetch an icon from a composable locally
 *
 */
@Composable
internal fun NetworkImage(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    imageUrl: String,
    placeholder: @Composable () -> Unit = {},
    error: @Composable () -> Unit = {},
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build(),
        contentDescription = contentDescription
    )
}



@Composable
fun HintResourceIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    tint: Color = LocalContentColor.current,
) {
    ResourceIcon(
        icon = icon, modifier = modifier, tint = tint
    )
}

@Preview
@Composable
fun PreviewLoaderImage() {
    AppTheme {
        Surface {
            Column(modifier = Modifier) {
                Text(
                    text = "Images",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Text(
                    text = "Generic Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                ResourceImage(image = R.mipmap.ic_launcher)

                Text(
                    text = "Network Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )


                Text(
                    text = "Local Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                HintResourceIcon(icon = R.mipmap.ic_launcher, modifier = Modifier.size(20.dp))

                Text(
                    text = "Hint Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                HintResourceIcon(icon = R.mipmap.ic_launcher, modifier = Modifier.size(20.dp))


                Text(
                    text = "CardWithImg",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
            }

        }
    }
}
