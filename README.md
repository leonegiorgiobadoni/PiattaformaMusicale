# 🎵 Music Platform - Spring Boot 4

A comprehensive full-stack web application for music management and streaming, developed using modern Java technologies. This system allows users to upload tracks, create custom playlists, and stream audio, while administrators manage access and content.

## 🚀 Tech Stack

* **Language:** Java 25 (Early Access)
* **Framework:** Spring Boot 4.0.2
* **Security:** Spring Security (Role-Based Access Control)
* **Database:** MySQL / MariaDB
* **Persistence:** Spring Data JPA / Hibernate 7
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Build Tool:** Maven

## ✨ Key Features

* **Secure Authentication:** Registration and Login system with password encryption.
* **User Verification:** Account approval system (only `authenticated = 1` users can access core features).
* **Audio Streaming:** Integrated HTML5 audio player with support for local MP3 file streaming.
* **Playlist Management:** Full CRUD functionality for personal music libraries.
* **Admin Dashboard:** Dedicated access for `SUPER_ADMIN` to manage genres and approve new members.
* **Data Seeding:** Automatic initialization of admin credentials and music genres on startup.

## 🛠️ Installation & Setup

### 1. Database Configuration
Import the `piattaformmusicale.sql` file into your MySQL server. Update your `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/piattaformamusicale
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD