package com.drund.storedemo.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drund.storedemo.R
import com.drund.storedemo.ui.theme.*
import com.drund.storedemo.viewmodel.CartViewModel
import com.drund.storedemo.model.CartItem
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.drund.storedemo.ui.components.AppGlideImage

/** Screen that displays the user's shopping cart */
// navController Navigation controller for handling screen transitions
// cartViewModel ViewModel for managing cart data

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartState by cartViewModel.cartState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        CommonHeader(
            cartItemCount = cartState.totalItemCount,
            navController = navController
        )

        if (cartState.items.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                EmptyCartContent(onNavigateBack = { navController.navigateUp() })
            }
            CommonFooter()
        } else {
            FilledCartContent(
                cartItems = cartState.items,
                subtotal = cartState.subtotal,
                onNavigateBack = { navController.navigateUp() },
                onIncreaseQuantity = { productId, quantity -> 
                    cartViewModel.updateQuantity(productId, quantity + 1)
                },
                onDecreaseQuantity = { productId, quantity -> 
                    cartViewModel.updateQuantity(productId, quantity - 1)
                },
                onRemoveItem = { productId -> 
                    cartViewModel.removeFromCart(productId)
                }
            )
        }
    }
}

/** Content to display when the cart is empty */
@Composable
private fun EmptyCartContent(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CartHeader(onNavigateBack = onNavigateBack)
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your cart is empty",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
        }
    }
}

/** Content to display when the cart has items */
@Composable
private fun FilledCartContent(
    cartItems: List<CartItem>,
    subtotal: Double,
    onNavigateBack: () -> Unit,
    onIncreaseQuantity: (Int, Int) -> Unit,
    onDecreaseQuantity: (Int, Int) -> Unit,
    onRemoveItem: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        CartHeader(onNavigateBack = onNavigateBack)
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))
            
            cartItems.forEachIndexed { index, cartItem ->
                Column {
                    if (index == 0) {
                        CartDivider()
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    
                    CartItemCard(
                        cartItem = cartItem,
                        onIncreaseQuantity = { onIncreaseQuantity(cartItem.product.id, cartItem.quantity) },
                        onDecreaseQuantity = { onDecreaseQuantity(cartItem.product.id, cartItem.quantity) },
                        onRemove = { onRemoveItem(cartItem.product.id) }
                    )
                    
                    if (index < cartItems.size - 1) {
                        CartDivider()
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            SubtotalText(subtotal = subtotal)
            CartDivider()
            
            Spacer(modifier = Modifier.height(0.dp))
            CheckoutButton()
            CartInfoText()
            StripeImage()
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        CommonFooter()
    }
}

/** Header section for the cart screen */
@Composable
private fun CartHeader(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BreadcrumbNavigation(onNavigateBack = onNavigateBack)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "MY CART",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
        )
    }
}

/** Breadcrumb navigation for the cart screen */
@Composable
private fun BreadcrumbNavigation(onNavigateBack: () -> Unit) {
    Row(
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
            text = "MY CART",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        )
    }
}

/** Divider used throughout the cart screen */
@Composable
private fun CartDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFB8D6FF),
        thickness = 1.dp
    )
}

/** Displays the subtotal amount */
@Composable
private fun SubtotalText(subtotal: Double) {
    Text(
        text = "SUBTOTAL: $${String.format("%.2f", subtotal)}",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

/** Checkout button component */
@Composable
private fun CheckoutButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /* Implement checkout */ },
            modifier = Modifier
                .width(156.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cartico),
                    contentDescription = "Cart icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Checkout",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

/** Informational text below the checkout button */
@Composable
private fun CartInfoText() {
    Text(
        text = "We will send you your card bundles two weeks after the tournament ends so we can verify the stats and images.",
        style = TextStyle(
            fontSize = 14.sp,
            color = Black,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 0.dp)
    )
}

/** Stripe logo image */
@Composable
private fun StripeImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.stripe),
            contentDescription = "Powered by Stripe",
            modifier = Modifier
                .size(width = 148.dp, height = 41.dp)
        )
    }
}

/** Card component that displays a cart item with quantity controls */
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Product image
            AppGlideImage(
                model = cartItem.product.image,
                contentDescription = cartItem.product.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            // Product details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Title and price row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = cartItem.product.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        ),
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", cartItem.product.price)}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Category
                Text(
                    text = cartItem.product.category,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                // Quantity controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuantityButton(
                        onClick = onDecreaseQuantity,
                        iconResId = R.drawable.minus,
                        contentDescription = "Decrease quantity"
                    )

                    Text(
                        text = cartItem.quantity.toString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    QuantityButton(
                        onClick = onIncreaseQuantity,
                        iconResId = R.drawable.plus,
                        contentDescription = "Increase quantity"
                    )

                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.trash),
                            contentDescription = "Remove item",
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }
            }
        }
    }
}

/** Button for increasing or decreasing quantity */
@Composable
private fun QuantityButton(
    onClick: () -> Unit,
    iconResId: Int,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(24.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}
