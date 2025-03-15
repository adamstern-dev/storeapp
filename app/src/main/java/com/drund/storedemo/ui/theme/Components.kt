package com.drund.storedemo.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drund.storedemo.R
import androidx.compose.ui.graphics.Color
import com.drund.storedemo.navigation.Screen

/** Common Header component used across screens */
@Composable
fun CommonHeader(cartItemCount: Int, navController: NavController? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue)
            .padding(horizontal = 20.dp, vertical = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "SG Tournament Cards Logo",
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .clickable { navController?.navigate(Screen.Home.route) }
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {
                navController?.navigate(Screen.Cart.route)
            })
        ) {
            Image(
                painter = painterResource(id = R.drawable.cartico),
                contentDescription = "Shopping Cart",
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.carttext),
                contentDescription = "Shopping Cart",
                modifier = Modifier
                    .width(51.dp)
                    .height(22.dp)
            )

            if (cartItemCount > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bluedot),
                        contentDescription = "Blue Dot",
                        modifier = Modifier
                            .width(16.dp)
                            .height(16.dp)
                    )
                    Text(
                        text = cartItemCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.offset(y = (-2).dp).padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

/** Common Footer component used across screens */
@Composable
fun CommonFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "SG Tournament Cards Logo",
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Text(
            text = "Copyright 2024 Sport Group, USA",
            color = Color.Gray,
            fontSize = 12.sp
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Privacy Policy",
                color = Color.Gray,
                fontSize = 12.sp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "Terms & Conditions",
                color = Color.Gray,
                fontSize = 12.sp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "Cookie Policy",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
} 