# Todo-App

TodoApp is a task management application that allows users to create, update, delete, and organize tasks. This project uses Kotlin and Jetpack Compose for the user interface, and is structured following the MVVM (Model-View-ViewModel) architecture pattern. Dependency injection is handled with Dagger Hilt.

## Functionalities

* Create new tasks
* Update existing tasks
* Delete tasks
* Mark tasks as completed

## Installation

1. Clone this repository:
`git clone https://github.com/elekiwi/Todo-App.git`

2.Open the project in Android Studio.

3.Sync the project with Gradle.

4.Run the app on an emulator or physical device.


## Project architecture

The project is structured using MVVM and Clean Architecture, which facilitates separation of concerns and improves code testability.

data: Contains the data models and access to local data.

  * local: Contains the data models and the implementation of the local database (Room).
    
domain: Contains the use cases that encapsulate the business logic.

presentation: Contains the application screens and their respective ViewModels.

## Tech stack

Dagger-Hilt for dependency injection.

RoomDB for persistence.

Flows and Coroutines for reactive and asyncronous programming.

Git for version control.

Unit testing with JUnit.

