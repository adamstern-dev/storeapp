package com.drund.storedemo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.drund.storedemo.ui.HomeScreen
import com.drund.storedemo.ui.StoreProductsScreen
import com.drund.storedemo.ui.ProductDetailsScreen
import com.drund.storedemo.ui.CartScreen
import com.drund.storedemo.viewmodel.CartViewModel
import com.drund.storedemo.viewmodel.ProductViewModel

// Main navigation setup for the app
// Tried using the new Navigation Component - much nicer than fragments!
@Composable
fun StoreNavigation(navController: NavHostController) {
    // Share VMs across all screens - keeps cart state when navigating
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home screen - entry point with featured products
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        
        // Product listing screen - shows all available products
        composable(route = Screen.StoreProducts.route) {
            StoreProductsScreen(
                navController = navController,
                productViewModel = productViewModel,
                cartViewModel = cartViewModel
            )
        }

        // Product details - shows full info for a single product
        // Takes productId from route to load specific product
        composable(
            route = Screen.ProductDetails.route + "/{productId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailsScreen(
                navController = navController,
                productId = productId,
                productViewModel = productViewModel,
                cartViewModel = cartViewModel
            )
        }

        // Shopping cart screen - shows items and checkout
        composable(route = Screen.Cart.route) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}

// Routes for all screens in the app
// Using sealed class makes it type-safe and prevents typos
sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object StoreProducts : Screen("store_products")
    object ProductDetails : Screen("product_details")
    object Cart : Screen("cart")

    // Helper to build routes with args
    // Makes navigation calls cleaner in the UI
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}


