package com.drund.storedemo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drund.storedemo.R
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.drund.storedemo.ui.theme.PrimaryBlue

@Composable
fun StarRating(
    rating: Float,
    numReviews: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            repeat(5) { index ->
                val starResource = if (index < rating) {
                    R.drawable.fillstar
                } else {
                    R.drawable.emptystar
                }
                Image(
                    painter = painterResource(id = starResource),
                    contentDescription = "Star ${index + 1}",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = "$numReviews Reviews",
            style = TextStyle(
                fontSize = 14.sp,
                color = PrimaryBlue
            )
        )
    }
} 