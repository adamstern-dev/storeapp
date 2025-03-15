package com.drund.storedemo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.drund.storedemo.api.Product
import com.drund.storedemo.ui.theme.*

/** A card component that displays product information in a consistent format */

// product The product data to display
// onCardClick Callback invoked when the card is clicked

@Composable
fun BundleCard(
    product: Product,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(AppDimensions.cornerRadiusSmall),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimensions.cardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            AppGlideImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.productImageHeight)
                    .clip(RoundedCornerShape(AppDimensions.cornerRadiusSmall)),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(AppDimensions.spacingLarge))
            
            // Product Title
            Text(
                text = product.title,
                style = AppTextStyles.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(AppDimensions.spacingMedium))
            
            // Product Description
            Text(
                text = product.description,
                style = AppTextStyles.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(AppDimensions.spacingMedium))
            
            // Product Price
            Text(
                text = "$${String.format("%.2f", product.price)}",
                style = AppTextStyles.priceLarge,
                textAlign = TextAlign.Center
            )
        }
    }
} 