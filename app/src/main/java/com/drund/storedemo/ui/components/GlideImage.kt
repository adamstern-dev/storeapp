package com.drund.storedemo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import androidx.compose.ui.graphics.painter.Painter

/** A wrapper around Glide's GlideImage for consistent image loading throughout the app */
// model The image source (URL, resource, etc.)
// contentDescription Content description for accessibility
// modifier Modifier for the image
// contentScale How the image should be scaled inside the bounds

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppGlideImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    GlideImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        loading = placeholder(painter = null),
        failure = placeholder(painter = null)
    )
} 