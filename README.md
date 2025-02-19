# ToDo Application

## Overview
Desktop application built using **Spring Boot** and **JavaFX**. Its purpose is to help users track their daily tasks and manage various task categories. Users can create, edit, and delete categories and tasks, keeping their to-do list organized and efficient.

## Features
The main features of the application include:
- **Create, Edit, and Delete Categories**: Users can create, update, or remove task categories for better organization.
- **Create, Edit, and Delete Tasks**: Users can manage their tasks within each category, adding new tasks or modifying existing ones.
- **Track Daily Tasks**: The app allows users to mark tasks as done and manage pending tasks, ensuring nothing is forgotten.

## Technologies Used
- **Spring Boot**: For backend management and dependency injection.
- **JavaFX**: For building the desktop application's user interface.

## Prerequisites
Before running the application, ensure that you have the following tools installed:
- **Java 17** or newer (required for compatibility with Spring Boot and JavaFX)
- **Maven**: To build and run the project

## How to run:
`$ mvn javafx:run`

## How to build Executable:
`$ jpackage --name DoItNow-v1.0 --input target --main-jar todo-app-1.0.0.jar --main-class org.springframework.boot.loader.launch.JarLauncher --type app-image --runtime-image <path-to-jdk> --icon src\main\resources\img\app_icon.ico`
