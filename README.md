# Pixo

Pixo is a modern, responsive Android application designed for exploring, searching, and managing high-quality wallpapers and images. Built with Jetpack Compose, the app adheres to official Android development best practices by implementing the Model-View-ViewModel (MVVM) architecture, a local caching database via Room, and seamless REST API communication with Retrofit.

---

## Features

- **Immersive Splash & Auth Flow:** Beautifully designed Landing, Login, and Signup screens utilizing custom gradient backgrounds, system-level status bar adaptations, and robust client-side form validations.
- **Dynamic Home Grid:** A fluid staggered grid layout (`LazyVerticalStaggeredGrid`) displaying trending wallpapers fetched from a remote server.
- **Robust Client-Side Search:** Instant, local-state filtering of posts with zero text clipping or input lag.
- **Caching & Offline Capability (Room):** Local persistence for notifications and posts, allowing smooth navigation even during network fluctuations.
- **SVG Image Support:** Custom Coil integration allowing direct rendering of scalable vector graphics (SVG) alongside traditional image formats.
- **Transient UI (Bottom Sheet):** The user profile settings are designed as a modal bottom sheet to maintain user context without heavy full-screen navigation.
- **System-Level Adaptations:** Global implementation of Edge-to-Edge layouts and safe system window insets padding.

---

## Tech Stack

- **UI Framework:** Jetpack Compose (Declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Asynchronous Execution:** Kotlin Coroutines & Flow (StateFlow / SharedFlow)
- **Local Database:** Room Database
- **Network Client:** Retrofit & OkHttp
- **JSON Parser:** Gson
- **Image Loading:** Coil (with SVG extension support)
- **Navigation:** Jetpack Compose Navigation

---

## Architecture Overview

Pixo strictly follows the separation of concerns principles via the MVVM architecture:

com.example.pixo │ ├── data │ ├── local │ │ ├── AppDatabase.kt # Room database
initialization │ │ ├── Converters.kt # Gson List converters │ │ ├──
NotificationDao.kt # Notifications queries │ │ ├── NotificationEntity.kt# Local
notification table schema │ │ └── PostDao.kt # Local post caching queries │ │ │
├── remote │ │ ├── ApiService.kt # Retrofit endpoints │ │ └── RetrofitClient.kt
# Network builder │ │ │ └── repository │ └── AppRepository.kt # Repository
acting as the single source of truth │ ├── model │ ├── HomePostsResponse.kt #
Home posts wrapper models │ ├── NotificationResponse.kt # Notification payload
model │ └── PostResponse.kt # Single post details entity and API model │ ├──
viewmodel │ ├── AuthViewModel.kt # Signin & Signup state management │ ├──
HomeViewModel.kt # Grid and search state management │ └──
NotificationViewModel.kt # Flow-to-state notifications caching management │ └──
view ├── MainActivity.kt # App entrypoint, bottom-sheet and navigation host ├──
BottomBar.kt # Navigation bar UI ├── HomeScreen.kt # Grid and Search UI ├──
LandingScreen.kt # Immersive splash screen UI ├── LoginScreen.kt # Login UI with
validators ├── SignupScreen.kt # Signup UI with validators ├──
NotificationScreen.kt # Rendered local notifications UI └── PostDetailScreen.kt
# Detailed image, interactions and similar grid UI


---

## Installation & Getting Started

### Prerequisites

- Android Studio Koala / Ladybug or newer
- JDK 17
- Android SDK 24 or newer (Target SDK 35)

### Clone the Repository

```bash
git clone https://github.com/your-username/pixo.git
cd pixo

Dependencies Setup

Make sure your build.gradle (Module:app) file contains the required
dependencies:

dependencies {
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-svg:2.6.0")
    
    implementation("com.google.code.gson:gson:2.10.1")
}

API References

The network requests point to the following mock server endpoints:

| Method | Endpoint                | Description                                  |
| :----- | :---------------------- | :------------------------------------------- |
| `POST` | `/api/v1/login`         | Authenticates a user and returns a token.    |
| `POST` | `/api/v1/signup`        | Creates a new user account.                  |
| `GET`  | `/api/v1/getAllNotif`   | Retrieves lists of notification updates.     |
| `GET`  | `/api/v1/getSinglePost` | Fetches full meta details for a single post. |
| `GET`  | `/api/v1/getAllPost`    | Returns list of posts for the home grid.     |

Local Database Schema (Room)

To handle local caching and offline operations, Pixo uses the Room library.
Non-primitive types, such as دسته‌بندی‌ها (List<String>), are seamlessly
converted back-and-forth into standard JSON text representations:

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}

