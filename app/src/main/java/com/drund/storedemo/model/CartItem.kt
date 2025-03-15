package com.drund.storedemo.model

import com.drund.storedemo.api.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1
) 