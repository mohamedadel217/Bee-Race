🐝 Bee Race App

The Bee Race App is a fun, interactive Android application built with modern development practices, showcasing a real-time race of bees! The app utilizes Jetpack Compose for UI, Koin for dependency injection, and Retrofit for API calls, adhering to the MVVM architecture pattern.

✨ Features

- Start Screen: Begin the race with a simple tap.

- Real-Time Race Updates: Fetches live updates about bee rankings during the race.

- Countdown Timer: Displays a countdown for the race duration.
- Dynamic Ranking Screen:
    
    - Bees are displayed with their respective colors and icons.
    
    - Top 3 bees are highlighted with a medal.

- Recaptcha Handling:

  - Automatically loads a captcha screen for verification when required.

  - Seamless redirection back to the race after solving the captcha.

- Winner Screen: Highlights the winner with their name and color.

- Error Handling:

   - Retry button for network or API-related errors.


🚀 Technologies Used

- Kotlin: Programming language for Android development.

- Jetpack Compose: Modern UI toolkit for building native Android interfaces.

- Koin: Dependency injection framework.

- Retrofit: For making network requests.

- OkHttp: For logging and request management.

- Coil: Image loading library for Compose.

- Navigation Component: For seamless screen transitions.

- Coroutines with Flow: Reactive streams for state management and asynchronous operations.

🛠️ Architecture
The app follows MVVM (Model-View-ViewModel) architecture with clean code principles:

- UI Layer: Compose screens (StartScreen, RaceScreen, CaptchaScreen, WinnerScreen, and ErrorScreen).
- ViewModel Layer: Business logic and state management using StateFlow.
- Domain Module: Contains use cases and business models for core logic.
- Data Module: Handles API communication and repository logic.
- Dependency Injection: Powered by Koin for modular and scalable architecture.

⚙️ Setup Instructions

1- Clone this repository:
git clone https://github.com/yourusername/bee-race-app.git

2- Open the project in Android Studio.
3- Sync Gradle and ensure all dependencies are installed.
4- Run the application on an emulator or physical device.

🧪 Testing

- Unit Tests:

  - Repository and Use Case tests for business logic validation.
  - View model

- UI Tests:
- Compose UI tests for screens:
  - StartScreen
  - RaceScreen
  - WinnerScreen
  - ErrorScreen
  - RecaptchaScreen
  
