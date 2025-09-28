# Gestion Salaries - Employee Management System

## 📋 Overview

A modern HR management system built with **Spring Boot** (Backend) and **React** (Frontend) for managing employees and generating employment certificates.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0+

### 1. Database Setup
```sql
CREATE DATABASE gestion_salaries CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 2. Backend
```bash
cd gestion-salaries-backend
.\mvnw.cmd spring-boot:run
```

### 3. Frontend
```bash
cd gestion-salaries-frontend
npm start
```

## 🔧 Configuration

### Backend (`application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_salaries
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8080
```

### Frontend
- API: `http://localhost:8080/api`
- Port: `3000`

## 📱 Features

### Employee Management
- ✅ Add/Edit/Delete employees
- ✅ Search and filter
- ✅ Modern UI with dark mode

### Attestation Generation
- ✅ 5 types of certificates
- ✅ PDF generation with JasperReports
- ✅ Download and management

### User Interface
- ✅ Material-UI design
- ✅ Responsive layout
- ✅ Dark/Light theme toggle

## 🔑 Login Credentials

- **Admin**: `admin` / `admin123`
- **HR**: Use signup form

## 📁 Project Structure

```
gestion-salaries/
├── gestion-salaries-backend/     # Spring Boot API
├── gestion-salaries-frontend/    # React UI
├── docs/                         # Documentation
│   ├── conception/               # Design & Architecture docs
│   ├── gantt/                    # Project timeline charts
│   └── uml/                      # UML diagrams
├── assets/                       # Static assets
│   ├── diagrams/                 # Generated diagrams
│   └── tools/                    # Technology logos
├── scripts/                      # Utility scripts
└── README.md
```

## 📚 Documentation

### Conception & Diagrams
- **[System Architecture](docs/conception/index.md)** - Complete design documentation
- **[Visual Diagrams](docs/conception/diagrams.md)** - Gantt charts, UML, and architecture diagrams
- **[Waterfall Methodology](docs/conception/waterfall.md)** - Development process documentation
- **[Tools & Technologies](docs/conception/tools.md)** - Technology stack overview

### Interactive Charts
- **[Gantt Chart](docs/gantt/index.html)** - Project timeline visualization
- **[UML Diagrams](docs/uml/README.md)** - System design diagrams

### Technical Reports
- **[LaTeX Report](RapportGestionSalaires/rapportGestionSalaires.pdf)** - Complete technical report

## 🐛 Troubleshooting

1. **Port 8080 in use**: Kill Java processes with `taskkill /f /im java.exe`
2. **Frontend not starting**: Run `cd gestion-salaries-frontend` then `npm start`
3. **Database errors**: Check MySQL connection and credentials

---

**Version**: 1.0.0 | **Status**: Production Ready ✅