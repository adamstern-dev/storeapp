package com.drund.storedemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.drund.storedemo.ui.theme.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import android.annotation.SuppressLint
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.drund.storedemo.api.Product
import com.drund.storedemo.navigation.Screen
import com.drund.storedemo.viewmodel.ProductViewModel
import com.drund.storedemo.viewmodel.CartViewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.drund.storedemo.ui.components.BundleCard

/** Store Products screen that displays a list of products */

// navController Navigation controller for handling screen transitions
// productViewModel ViewModel for managing product data and state
// cartViewModel ViewModel for managing cart data and state

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StoreProductsScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val productState by productViewModel.productState.collectAsState()
    val cartState by cartViewModel.cartState.collectAsState()
    
    // product click handler to avoid recreating it on each recomposition
    val onProductClick = remember(navController, productViewModel) {
        { product: Product ->
            productViewModel.setSelectedProduct(product)
            navController.navigate(Screen.ProductDetails.withArgs(product.id))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Header with cart count
        CommonHeader(
            cartItemCount = cartState.totalItemCount,
            navController = navController
        )

        // Content area
        if (productState.error != null && productState.products.isEmpty()) {
            ErrorState(
                error = productState.error,
                onRetry = { productViewModel.retryLoading() }
            )
        } else {
            ProductList(
                products = productState.products,
                isLoading = productState.isLoading,
                onProductClick = onProductClick
            )
        }
    }
}

/** Displays an error state with retry option */
@Composable
private fun ErrorState(
    error: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimensions.spacingSmall)
            ) {
                Text(
                    text = error ?: "An error occurred",
                    style = TextStyle(color = Color.Red)
                )
                Button(onClick = onRetry) {
                    Text("Retry")
                }
            }
        }
        CommonFooter()
    }
}

/** Displays a scrollable list of products with loading indicator */
@Composable
private fun ProductList(
    products: List<Product>,
    isLoading: Boolean,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = AppDimensions.paddingLarge),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.spacingLarge)
    ) {
        // Product items
        items(products) { product ->
            Box(modifier = Modifier.padding(horizontal = AppDimensions.paddingLarge)) {
                BundleCard(
                    product = product,
                    onCardClick = { onProductClick(product) }
                )
            }
        }

        // Loading indicator
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimensions.paddingLarge),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Footer
        item {
            CommonFooter()
        }
    }
}