# MoodBloom Android Application

This project is an Android mobile application called **MoodBloom**, designed to help students improve their mental well-being through mood tracking, habit management, guided breathing exercises, and AI-powered insights.

## Folder Structure

The table below provides an overview of the main files and directories in the project:

| Path | Description |
|------|-------------|
| `your-first-sketch-Ihsaan-Rashid-main/` | Root project directory |
| ├── `build.gradle.kts` | Project-level Gradle configuration |
| ├── `settings.gradle.kts` | Project settings |
| ├── `gradle.properties` | Gradle system properties |
| ├── `gradlew / gradlew.bat` | Gradle wrapper scripts |
| ├── `coder_info.txt` | Developer info file |
| └── `app/` | Main Android app module |
| &nbsp;&nbsp;&nbsp;&nbsp;├── `build.gradle.kts` | App-level Gradle config |
| &nbsp;&nbsp;&nbsp;&nbsp;├── `google-services.json` | Firebase configuration file |
| &nbsp;&nbsp;&nbsp;&nbsp;├── `proguard-rules.pro` | ProGuard configuration |
| &nbsp;&nbsp;&nbsp;&nbsp;└── `src/` | Source folder for app code |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── `main/` | Main source set |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│   ├── `AndroidManifest.xml` | App manifest |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│   └── `java/com/example/moodbloom/` | Main Kotlin source files |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│       ├── `MainActivity.kt`, `MainViewModel.kt`, etc. | Core activity/view model |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│       ├── `domain/models/` | Data models like mood and habit |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│       ├── `extension/` | Reusable Kotlin extensions |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│       ├── `navigation/` | App navigation components |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│       └── `presentation/` | UI components and screens |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│           ├── `components/` | UI elements like buttons, headers, loaders |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│           └── `screens/` | Feature-specific screens (home, log mood) |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── `androidTest/` | Instrumentation tests |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── `ExampleInstrumentedTest.kt` | Basic test for app setup |

## res/ and XML Folder Structure

The `res/` directory contains application resources such as drawable assets, values, and configurations. Since this project uses Jetpack Compose, there is no `layout/` folder. Below is the updated breakdown based on the actual project structure:

| Path | Description |
|------|-------------|
| `app/src/main/res/` | Main resource directory |
| ├── `drawable/` | Vector drawables and image assets |
| ├── `mipmap-hdpi/` to `mipmap-xxxhdpi/` | Launcher icons for various screen densities |
| ├── `raw/` | Directory for raw assets (e.g., audio, video files) |
| ├── `values/` | Contains global XML configuration files |
| │   ├── `colors.xml` | Defines color palette used in the app |
| │   ├── `strings.xml` | Stores all the string resources |
| │   ├── `themes.xml` | App-wide theme and styling definitions |
| │   └── `arrays.xml` | Stores array-based resources such as string arrays |
| └── `xml/` | Configuration files such as file paths, backups, etc. |

## Technologies Used
- **Kotlin** – This is the programming language I used to code the application.
- **Jetpack Compose** – UI toolkit
- **Firebase (Auth, Firestore, Cloud Messaging)** – These are the Backend services that are featured within the application.
- **Gemini AI API** – These are the AI powered insights that analysed the entries that the user has logged.
- **MVVM Architecture** – This is the software project structure and data flow that I used for this project.

## Notes
- Ensure that you have an active internet connection when running the app.
- All required dependencies are included in the `build.gradle.kts` files.
