# Location-Based and Time-Based Reminder App

## Overview
This app allows users to set reminders that can be triggered either based on **time** or **location**. The app uses **Firebase** for storage and **Google Location Services** to track the user's current location. Once the user reaches a target location or the scheduled time arrives, a **push notification** is triggered to remind them about the task. The app provides a flexible way to ensure important reminders are received at the right time or place.

## Features

- **Time-Based Reminders**: Set reminders that trigger at a specific date and time. Useful for tasks that need to be completed at a particular moment, such as meetings or appointments.
  
- **Location-Based Reminders**: Set reminders that are triggered when the user arrives at a specific location. For example, you can set a reminder to pick up groceries when you get close to the store.

- **Current Location**: Use your current location to set location-based reminders easily. The app fetches your location and helps set a geofence around it.

- **Location Browsing**: Search for a specific location and set a reminder for that location, making it easy to set reminders at places that aren’t currently nearby.

- **Push Notifications**: Receive timely push notifications to alert you when your reminder is triggered, whether it’s based on time or location.

## Tech Stack

- **Java**: The app is developed using Java for Android app development.

- **XML**: UI design is created using XML to ensure the app is visually appealing and user-friendly.

- **Firebase**: Firebase is used for the backend to store reminders in real-time and send push notifications to the users.

- **Google Location Services**: Utilized to obtain the user’s current location and to set geofencing for location-based reminders.

- **Android Notifications**: To trigger push notifications for time-based or location-based reminders, notifying the user at the right time.

## Setup and Installation

### Prerequisites
- **Android Studio**: Make sure you have Android Studio installed for development.
- **Firebase Project**: Set up a Firebase project to integrate Firebase Database and Cloud Messaging for notifications.

