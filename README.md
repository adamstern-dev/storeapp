
# Store Demo App

A modern e-commerce mobile application built with Kotlin and Jetpack Compose that demonstrates best practices in Android development.

## Features

- **Product Browsing**: Browse products fetched from the Fake Store API
- **Product Details**: View detailed information about each product
- **Shopping Cart**: Add products to cart, update quantities, and remove items
- **Responsive UI**: Modern, clean UI built with Jetpack Compose
- **Offline Support**: Cart data is maintained locally

## Notes
- This application fetches products from fakeAPI and then you can see them on Store Product Screen,
- I tried to follow the figma as much as possible within time limit
- I checked the FakeAPI but they don't send Product Popularity etc. that's why I didn't show it on the Product Screen
- I think there should be a Continue Shopping button on Cart so that users can go back to the Product Page again.

### Platform Requirements
- Minimum SDK: 26
- Target SDK: 35
- Language: Kotlin
- UI Framework: Jetpack Compose

### Libraries
- **Ktor**: For network requests to the Fake Store API
- **Kotlin Serialization**: For parsing JSON data
- **Glide**: For efficient image loading and caching
- **Navigation Compose**: For screen navigation
- **Material 3**: For modern UI components


## Architecture

The application follows a clean architecture approach with the following components:

### Data Layer
- **API Service**: `StoreApiService` handles communication with the Fake Store API
- **Repository**: `StoreRepository` manages data operations
- **Models**: Data classes for products and cart items

### UI Layer
- **ViewModels**: `ProductViewModel` and `CartViewModel` manage UI state
- **Screens**: Compose screens for different parts of the application
- **Components**: Reusable UI components

### Navigation
- Navigation between screens is handled using Navigation Compose


### Home Screen
- Displays promotional content and navigation to the store

### Store Products Screen
- Displays a list of products fetched from the API
- Each product is displayed in a card with image, title, description, and price
- Clicking on a product navigates to the product details screen

### Product Details Screen
- Displays detailed information about a product
- Shows product image, title, category, price, rating, and description
- Allows adding the product to the cart

### Cart Screen
- Displays items in the cart
- Allows updating quantities and removing items
- Shows subtotal and checkout button
- Displays an empty state when the cart is empty

## Implementation Details

### API Integration
- Products are fetched from the Fake Store API (https://fakestoreapi.com/products)
- The application handles API responses and error states

### Cart Management
- Cart data is maintained locally in the `CartViewModel`
- Cart operations include adding items, updating quantities, and removing items
- Cart state is preserved across the application

### Image Loading
- Images are loaded efficiently using Glide
- Custom `AppGlideImage` component for consistent image loading

### UI Components
- Custom components for product cards, cart items, and more
- Consistent styling using theme variables

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application on an emulator or physical device
