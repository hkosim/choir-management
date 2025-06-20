# Choir Management App

Welcome to my personal project: Choir Management App!
This is a full-stack web application for managing choir members with their respective attendance, rehearsals,
performances and communications.

## 📚 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Screenshots](#-screenshots)
- [To-Do / Upcoming Updates](#-to-do--upcoming-updates)
- [Project Structure](#-project-structure)

## ✨ Features

- Member login
- Rehearsals and performances scheduling
- Attendance tracking

## 🔧 Tech Stack

**Frontend**: Angular 20, TypeScript, HTML/CSS  
**Backend**: Spring Boot 3.5, Java, REST API  
**Database**: MySQL  
**Other**: Docker (optional), Spring Security

## 🖼️ Screenshots
Appointment List (User)
![Appointment List (User)](doc/appointment-list-user.jpg)

Appointment List (Admin)
![Appointment List (Admin)](doc/appointment-list-admin.jpg)

Appointment Edit Page (Admin)
![Appointment Edit Page (Admin)](doc/appointment-edit.jpg)

Current API-Documentation
![Current API-Documentation](doc/current-api-doc.jpg)

## 🗓️ To-Do / Upcoming Updates
- [ ] (Frontend) Interceptors
- [ ] (Backend) JWT-Authentication
- [ ] Add unit and integration tests
- [ ] Dockerize full application for easier deployment

Upcoming features:
- [ ] Appointments (rehearsals or performances) CRUD for admins
- [ ] Member registration and member management
- [ ] Data visualization for the attendance
- [ ] More robust error handling
- [ ] Implement calendar view for rehearsals & performances

## 📁 Project Structure


Here is the structure of this project:
```markdown
choir-management/ # Spring Boot backend
├── frontend/ # Angular frontend
│ ├── src/
│ └── angular.json
│── src/...
│── pom.xml
└── README.md
```