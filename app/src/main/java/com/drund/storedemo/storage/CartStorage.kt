package com.drund.storedemo.storage

import android.content.Context
import android.content.SharedPreferences
import com.drund.storedemo.model.CartItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Handles saving/loading cart data to device storage
 * Using SharedPrefs for simplicity - could use Room later if needed
 */
class CartStorage(context: Context) {
    // Basic prefs setup - nothing fancy
    private val prefs: SharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    
    // Need to ignore unknown keys in case we change the model later
    // Learned this the hard way after a crash in my last app!
    private val json = Json { ignoreUnknownKeys = true }

    // Saves cart items as JSON string
    // Not the most efficient but works fine for our small cart
    fun saveCart(items: List<CartItem>) {
        val cartItemsJson = json.encodeToString(items)
        prefs.edit().putString("cart_items", cartItemsJson).apply()
    }

    // Loads cart from prefs
    // Returns empty list if nothing saved yet or if parsing fails
    fun getCart(): List<CartItem> {
        val cartItemsJson = prefs.getString("cart_items", null)
        return if (cartItemsJson != null) {
            try {
                json.decodeFromString(cartItemsJson)
            } catch (e: Exception) {
                // Something went wrong with parsing
                // Probably changed the data model - just start with empty cart
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    // Wipes cart data
    // Called after checkout or when user wants to start fresh
    fun clearCart() {
        prefs.edit().remove("cart_items").apply()
    }
} 