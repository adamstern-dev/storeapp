package com.drund.storedemo.ui

import androidx.compose.foundation.background
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.drund.storedemo.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.TextStyle
import com.drund.storedemo.ui.theme.*
import com.drund.storedemo.viewmodel.CartViewModel

/**  Main home screen of the application  **/

// "navController" Navigation controller for handling screen transitions
// "cartViewModel" ViewModel for managing cart data

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartState by cartViewModel.cartState.collectAsState()

    Scaffold(
        topBar = { 
            CommonHeader(
                cartItemCount = cartState.totalItemCount,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero section
            BaseballCardsShowcase()
            
            // Main content sections
            SportGroupSection()
            ShopNowButton(navController)
            CombinedImagesSection()
            GetStartedSection()
            ShopNowButton(navController)
            
            // Footer
            CommonFooter()
        }
    }
}

/** Displaying the hero image here  */

@Composable
private fun BaseballCardsShowcase() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.homeheroimage),
            contentDescription = "Baseball Cards Showcase",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

/** Displaying the sport group tournament cards here  */
@Composable
private fun SportGroupSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SPORT GROUP\nTOURNAMENT CARDS",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
                color = DarkBlue
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Collect unique baseball cards to showcase your favorite future all-star stats each tournament.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
                color = Black,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

/** Button of "Shop Now" that navigates to the Store products screen */
@Composable
private fun ShopNowButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { navController.navigate("store_products") },
            modifier = Modifier
                .width(137.dp)
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Shop Now",
                style = TextStyle(
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(40.dp))
}

/** I have added the Select Player Section as an Image to save time */
@Composable
private fun CombinedImagesSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        // Here's the Steps Selection Image
        Image(
            painter = painterResource(id = R.drawable.homesection1),
            contentDescription = "Steps Section",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        
        // Here's the Baseball Player Image section
        Image(
            painter = painterResource(id = R.drawable.homesection2),
            contentDescription = "Advertisement Section",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

/** "Get Started" section with informational text */
@Composable
private fun GetStartedSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GET STARTED",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
                color = DarkBlue
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "At every event, we take the opportunity to capture photos of all our players attending. We use these photos to create each player's one-of-a-kind personal baseball cards fitted with all their stats for each tournament. Make sure to get your custom card bundles of your favorite player for all the SG events they are in.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
                color = Black,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
