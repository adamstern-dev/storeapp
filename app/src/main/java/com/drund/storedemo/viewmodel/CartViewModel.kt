package com.drund.storedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.drund.storedemo.api.Product
import com.drund.storedemo.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Current state of the shopping cart
data class CartState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val totalItemCount: Int = 0
)

/**
 * It handles all shopping cart operations.
 * It keeps track of items and calculates the total price.
 */
class CartViewModel : ViewModel() {
    // Using StateFlow since it works better with Compose than LiveData
    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState

    // Adds a product to cart or increments quantity if already there
    // TODO: Maybe add a max quantity limit? 99 seems reasonable
    fun addToCart(product: Product) {
        _cartState.update { currentState ->
            val existingItem = currentState.items.find { it.product.id == product.id }
            val updatedItems = if (existingItem != null) {
                // Product already in cart - just bump the qty
                currentState.items.map { item ->
                    if (item.product.id == product.id) {
                        item.copy(quantity = item.quantity + 1)
                    } else {
                        item
                    }
                }
            } else {
                // New item - add it with qty of 1
                currentState.items + CartItem(product = product, quantity = 1)
            }
            
            val totalCount = updatedItems.sumOf { it.quantity }
            
            currentState.copy(
                items = updatedItems,
                subtotal = calculateSubtotal(updatedItems),
                totalItemCount = totalCount
            )
        }
    }

    // Completely removes an item from cart regardless of quantity
    fun removeFromCart(productId: Int) {
        _cartState.update { currentState ->
            val updatedItems = currentState.items.filter { it.product.id != productId }
            val totalCount = updatedItems.sumOf { it.quantity }
            
            currentState.copy(
                items = updatedItems,
                subtotal = calculateSubtotal(updatedItems),
                totalItemCount = totalCount
            )
        }
    }

    // Sets exact quantity for an item
    // Removes item if quantity <= 0
    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            // No point keeping items with 0 qty - just remove them
            removeFromCart(productId)
            return
        }

        _cartState.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (item.product.id == productId) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }
            val totalCount = updatedItems.sumOf { it.quantity }
            
            currentState.copy(
                items = updatedItems,
                subtotal = calculateSubtotal(updatedItems),
                totalItemCount = totalCount
            )
        }
    }

    // Simple price calculation - price × qty for each item

    private fun calculateSubtotal(items: List<CartItem>): Double {
        return items.sumOf { it.product.price * it.quantity }
    }

    // Wipes the cart clean - used after checkout or when user wants to start over
    fun clearCart() {
        _cartState.value = CartState()
    }
}