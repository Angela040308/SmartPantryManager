# Smart Pantry Manager

## About the App
Smart Pantry Manager is an Android application developed using Java to help users manage pantry ingredients and reduce food waste. Users can add, edit, and delete ingredients, track quantities and expiry dates, and receive recipe suggestions based on the ingredients available in their pantry.

## Features
- Add, edit, and delete pantry ingredients.
- View pantry ingredients using a RecyclerView.
- Store ingredient information persistently.
- Suggest recipes only when all required ingredients are available in sufficient quantities.
- View recipe ingredients and preparation instructions.
- Change measurement unit preferences.
- Enable or disable expiry-date settings.
- Display a helpful message when no recipes match the pantry.

## Technologies Used
- Java
- Android Studio
- XML for user interface layouts
- SQLite for local database storage
- RecyclerView and custom adapters
- Android Intents for navigation
- SharedPreferences for saving settings

## Database Choice
SQLite was chosen because it is built into Android, supports structured local data storage, and does not require an internet connection or a separate database server. It allows pantry ingredients and recipes to be stored and retrieved efficiently.

## Requirements
- Android Studio
- Android SDK compatible with the project
- An Android emulator or physical Android device

## How to Run the Application
1. Clone or download this repository from GitHub.
2. Open Android Studio.
3. Select **Open** and choose the downloaded `SmartPantryManager` project folder.
4. Allow Gradle to sync and finish loading the project.
5. Connect an Android device or start an emulator.
6. Click **Run** to build and launch the application.

## Recipe Matching
The application compares the ingredients and quantities stored in the pantry with each recipe's requirements. A recipe is suggested only when all required ingredients are available in sufficient quantities. Recipes with missing ingredients or insufficient quantities are excluded.

## Data Storage
SQLite stores pantry ingredient information and recipe data locally. SharedPreferences stores user settings, allowing preferences to remain available when the application is reopened.

## Repository
Source code: https://github.com/Angela040308/SmartPantryManager

## Author
Developed as part of the Mobile Application Development 700 practical assignment.
