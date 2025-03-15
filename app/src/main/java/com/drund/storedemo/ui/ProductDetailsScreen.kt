package com.drund.storedemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drund.storedemo.ui.theme.*
import android.annotation.SuppressLint
import com.drund.storedemo.viewmodel.CartViewModel
import com.drund.storedemo.viewmodel.ProductViewModel
import com.drund.storedemo.ui.theme.CommonHeader
import com.drund.storedemo.ui.theme.CommonFooter
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import com.drund.storedemo.ui.components.StarRating
import com.drund.storedemo.ui.components.AppGlideImage
import androidx.compose.foundation.shape.RoundedCornerShape

/** Screen that displays detailed information about a product */
// navController Navigation controller for handling screen transitions
// productId ID of the product to display
// productViewModel ViewModel for managing product data
// cartViewModel ViewModel for managing cart data

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    productId: Int,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val productState by productViewModel.productState.collectAsState()
    val cartState by cartViewModel.cartState.collectAsState()
    val product = productState.selectedProduct
    
    // Check if product is already in cart
    val isInCart = remember(cartState.items, product) {
        cartState.items.any { it.product.id == product?.id }
    }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            CommonHeader(
                cartItemCount = cartState.totalItemCount,
                navController = navController
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (product != null) {
                    ProductContent(
                        product = product,
                        isInCart = isInCart,
                        onAddToCart = {
                            if (isInCart) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Item already in cart",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            } else {
                                cartViewModel.addToCart(product)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Item added to cart",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                } else {
                    LoadingIndicator()
                }

                Spacer(modifier = Modifier.height(32.dp))
                CommonFooter()
            }
        }
        
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

/** Displays the product content including image, title, price, and description */
@Composable
private fun ProductContent(
    product: com.drund.storedemo.api.Product,
    isInCart: Boolean,
    onAddToCart: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Breadcrumb navigation
        BreadcrumbNavigation(
            productTitle = product.title,
            onNavigateBack = onNavigateBack
        )

        // Product image
        AppGlideImage(
            model = product.image,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product title
        Text(
            text = product.title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product category
        Text(
            text = product.category,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product price
        Text(
            text = "$${String.format("%.2f", product.price)}",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product rating
        StarRating(
            rating = product.rating.rate.toFloat(),
            numReviews = product.rating.count,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product description
        Text(
            text = product.description,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.DarkGray,
                lineHeight = 24.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Add to cart button
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .width(149.dp)
                .height(54.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Add to Cart",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

/** Displays a breadcrumb navigation bar */
@Composable
private fun BreadcrumbNavigation(
    productTitle: String,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SHOP",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.clickable(onClick = onNavigateBack)
        )
        Text(
            text = " > ",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            )
        )
        Text(
            text = productTitle,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** Displays a loading indicator */
@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryBlue)
    }
}
