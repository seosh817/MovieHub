<h1 align="center">MovieHub</h1>

<p align="center">
  <a href="https://android.com">
    <img src="https://img.shields.io/badge/Platform-Android-brightgreen?logo=android"
      alt="Platform" />
  </a>

  <a href="https://android-arsenal.com/api?level=24">
    <img src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" alt="API"/>
  </a>

  <a href="https://github.com/seosh817/MovieHub/actions">
    <img src="https://github.com/seosh817/MovieHub/workflows/Android%20Build/badge.svg"
      alt="Build Status" />
  </a>

  <a href="https://opensource.org/licenses/Apache-2.0">
    <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" 
      alt="License" />
  </a>
</p><br>

<p align="center">🎬 An android app for exploring movies using the movie database API Implemented by Clean Architecture, MVVM, Compose, Coroutines, Flow, Hilt, Jetpack(Room, Datastore)</p><br/><br/>

# Tech-stack

<img src="/previews/preview_1.2.0.gif" align="right" width="320" alt="preview"/>

- [Kotlin](https://kotlinlang.org/) - is a modern and pragmatic programming language developed by JetBrains. It is fully interoperable with Java and widely adopted for Android app development.
- **Asynchronous**:
  - [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Kotlin's coroutine library simplifies asynchronous programming and enhances concurrency management.
- **Networking**:
  + [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client library for Android and Java, facilitating easy and efficient RESTful API calls.
  + [OkHttp](https://github.com/square/okhttp) - An HTTP client library often used in conjunction with Retrofit for networking tasks.
- **Serialization**:
  - [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlinx Serialization is a Kotlin library that provides a powerful and flexible way to serialize and deserialize Kotlin data classes.
- **Image loading**:
  + [Coil](https://github.com/coil-kt/coil) - An image loading library for Android, designed to be lightweight and efficient in handling image processing tasks.
- [Jetpack](https://developer.android.com/jetpack/):
  + [Compose](https://developer.android.com/jetpack/compose) - A modern UI toolkit for declaring UI components in a declarative manner.
  + [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - A tool for handling actions related to the app's lifecycle changes.
  + [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Used to store and manage UI-related data in a lifecycle-aware way.
  + [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - A tool for managing pagination in large datasets.
  + [Room](https://developer.android.com/training/data-storage/room) - An ORM library built on SQLite for handling local databases.
  + [Datastore](https://developer.android.com/topic/libraries/architecture/datastore) - A modern data storage library for data management.
- **Architecture**:
  - MVVM(Model-View-ViewModel)
  - [Android Architecture components](https://developer.android.com/topic/libraries/architecture) -  A set of libraries providing architecture components for Android app development.
  - [Android KTX](https://developer.android.com/kotlin/ktx) - Kotlin extensions for Android app development, enhancing Kotlin usage convenience.
- **Dependency Injection**:
  + [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - A dependency injection library for Android apps, providing a simple and efficient approach based on Dagger.
- [Protocol buffers](https://developers.google.com/protocol-buffers) - protobuf is a method developed by Google for serializing structured data, similar to XML or JSON. It is designed to be more efficient and compact than traditional serialization formats.
- **Testing**:
  - [JUnit4](https://junit.org/junit4/) - JUnit4 is a unit testing framework for Java and Kotlin. It is used to write unit tests for business logic or utility methods in Android apps.
  - [Robolectric](http://robolectric.org/) - Robolectric is a unit testing framework for Android. It provides a simulated Android environment for testing, allowing you to run tests on Android framework-dependent code without the need for a real Android device or emulator.
  - [Espresso](https://developer.android.com/training/testing/espresso) - Espresso is a testing framework for Android UI tests. It is used to test the behavior of user interfaces by interacting with UI elements and verifying the outcomes.
- **Static analysis tools**:
  - [Detekt](https://github.com/arturbosch/detekt#with-gradle) - A tool for static analysis of Kotlin code, verifying code complexity and searching for code smells.
  - [Spotless](https://github.com/diffplug/spotless) - A code formatter and verifier tool to keep your code clean and consistent.
- **Gradle**:
  - [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) + Gradle Convetion Plugins + Custom tasks
- **Dependency
  versions**:
  - [Version catalog TOML file format](https://docs.gradle.org/current/userguide/platforms.html) - A convenient method in Gradle for managing dependency versions
- **Continuous Integration**:
  - [GitHub Actions](https://docs.github.com/en/actions) - A CI tool provided by GitHub for automating workflows.

# Project Configuration

## Setting up the API Key

This project requires The Movie Database (TMDB) API key to access external APIs. Follow the steps below to configure the API key:

1. Create a `local.properties` file in the project root directory.
2. Add the `TMDB_API_KEY` to the `local.properties` file as shown below:

    ```properties
    TMDB_API_KEY=your TMDB_API key
    ```
3. Build and run the project.

# TODO

- [x] Offline-first caching
- [ ] Add localization support (Korean, English, Japanese)
- [ ] Implement Media3 for video playback

# Inspiration

This project is developed with inspiration from the [The Movies2](https://github.com/skydoves/TheMovies2) project.

# License

```
Copyright 2024 seosh817 (Seunghwan Seo)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

# Contribution

Feel free to file an [issue](https://github.com/seosh817/MovieHub/issues) if you find a
problem or make [pull requests](https://github.com/seosh817/MovieHub/pulls).<br>
All contributions are welcome 😁
