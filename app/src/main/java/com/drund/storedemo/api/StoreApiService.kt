package com.drund.storedemo.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

// Product model matching the FakeStore API response
// Had to make rating optional since some products don't have it
@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating = Rating()
)

// Rating info from the API
// Default values help avoid null checks everywhere
@Serializable
data class Rating(
    val rate: Double = 0.0,
    val count: Int = 0
)

/**
 * Handles all API calls to the Fake Store API
 * Using Ktor instead of Retrofit - wanted to try something new!
 */
class StoreApiService {
    // Setup Ktor client with JSON parsing
    // ignoreUnknownKeys=true saves us when the API changes
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // FakeStore API base URL
    // TODO: Move this to a config file if we add more environments
    private val baseUrl = "https://fakestoreapi.com"

    // Fetch all products
    // Returns empty list on error so UI doesn't crash
    suspend fun getProducts(): List<Product> {
        return try {
            client.get("$baseUrl/products").body()
        } catch (e: Exception) {
            // Log this properly in a real app
            emptyList() // Return an empty list in case of error
        }
    }

    // Get details for a specific product
    // API sometimes times out, so we handle that gracefully
    suspend fun getProductDetails(productId: Int): Product? {
        return try {
            client.get("$baseUrl/products/$productId").body()
        } catch (e: Exception) {
            // Should add proper logging here
            null // Return null in case of error
        }
    }
}