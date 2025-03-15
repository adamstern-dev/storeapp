package com.drund.storedemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drund.storedemo.api.Product
import com.drund.storedemo.api.StoreApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Holds all product-related state in one place
// Makes it easier to handle loading/error states
data class ProductState(
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


//  Main VM for product browsing and details.
//  Handles API calls and maintains product state.

class ProductViewModel : ViewModel() {
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState
    
    // Could inject this later if we need testing, but this works for now
    private val storeApiService = StoreApiService()

    init {
        // Load products right away so we have data when screen opens
        loadProducts()
    }

    // Fetches products from API and updates state accordingly
    private fun loadProducts() {
        viewModelScope.launch {
            try {
                _productState.update { it.copy(isLoading = true) }
                val products = storeApiService.getProducts()
                _productState.update { currentState ->
                    currentState.copy(
                        products = products,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                // Something went wrong - show error and let user retry
                _productState.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load products"
                ) }
            }
        }
    }

    // User hit the retry button after an error
    fun retryLoading() {
        loadProducts()
    }

    // Called when user selects a product to view details
    fun setSelectedProduct(product: Product) {
        _productState.update { currentState ->
            currentState.copy(selectedProduct = product)
        }
    }

    // Convenience method for screens that need the selected product
    // TODO: Consider using savedStateHandle for this instead
    fun getSelectedProduct(): Product? {
        return _productState.value.selectedProduct
    }

    // Resets and reloads products - useful for pull-to-refresh
    // or when we need to clear filters (if we add them later)
    fun clearProducts() {
        _productState.update { currentState ->
            currentState.copy(
                products = emptyList(),
                selectedProduct = null
            )
        }
        loadProducts()
    }
} 