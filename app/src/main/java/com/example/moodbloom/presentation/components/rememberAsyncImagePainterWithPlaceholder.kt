package com.example.moodbloom.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest


@Composable
fun rememberAsyncImagePainterWithPlaceholder(
    model: Any,
    placeholder: Painter,
    error: Painter,
): Painter {
    val context = LocalContext.current
    return rememberAsyncImagePainter(
        model = model,
        placeholder = placeholder,
        error = error,
        onError = {
          println( it.result)
        },
        imageLoader = ImageLoader(context)
    )
}

@Composable
fun rememberAsyncSvgImagePainterWithPlaceholder(
    model: Any,
    placeholder: Int,
    error: Int,
): Painter {
    val context = LocalContext.current
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(model)
            .placeholder(placeholder)
            // ✅ Enable Caching
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCacheKey(model.toString())
            .error(error)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        imageLoader = ImageLoader(context),
        placeholder = painterResource(placeholder),
        error = painterResource(error),
        onError = { println("Error loading image: ${it.result.throwable}") }
    )
}







