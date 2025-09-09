# Gestion Salaries - Employee Management System

## 📋 Project Overview

**Gestion Salaries** is a comprehensive employee management system built with Spring Boot (Backend) and React (
Frontend). The system allows HR managers to manage employee information and generate various types of employment
certificates (attestations).

## 🏗️ Architecture

### Backend (Spring Boot)

- **Framework**: Spring Boot 3.x
- **Database**: MySQL with H2 for testing
- **Build Tool**: Maven
- **Java Version**: 17+
- **Port**: 8080

### Frontend (React)

- **Framework**: React 19.x
- **UI Library**: Material-UI (MUI)
- **Build Tool**: npm
- **Port**: 3000

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- MySQL 8.0 or higher
- Maven (or use Maven wrapper)

### 1. Database Setup

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS gestion_salaries
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE gestion_salaries;

-- Table employe
CREATE TABLE IF NOT EXISTS employe
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom           VARCHAR(100) NOT NULL,
    prenom        VARCHAR(100) NOT NULL,
    cin           VARCHAR(20)  NOT NULL UNIQUE,
    poste         VARCHAR(100) NOT NULL,
    service       VARCHAR(100) NOT NULL,
    date_embauche DATE         NOT NULL
);

-- Table attestation
CREATE TABLE IF NOT EXISTS attestation
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    employe_id       BIGINT      NOT NULL,
    type_attestation VARCHAR(20) NOT NULL,
    date_generation  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chemin_fichier   VARCHAR(255),

    CONSTRAINT fk_employe FOREIGN KEY (employe_id) REFERENCES employe (id)
        ON DELETE CASCADE
);

-- Insert test data
INSERT INTO employe (nom, prenom, cin, poste, service, date_embauche)
VALUES ('Dupont', 'Jean', 'AB123456', 'Développeur', 'IT', '2023-01-15'),
       ('Martin', 'Marie', 'CD789012', 'Chef de Projet', 'Management', '2022-06-01'),
       ('Bernard', 'Pierre', 'EF345678', 'Analyste', 'Business', '2023-03-20');
```

### 2. Backend Setup

```bash
cd gestion-salaries-backend

# Using Maven wrapper (Windows)
.\mvnw.cmd clean install

# Using Maven wrapper (Linux/Mac)
./mvnw clean install

# Run the application
.\mvnw.cmd spring-boot:run
# or
./mvnw spring-boot:run
```

**Backend Configuration** (`application.properties`):

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_salaries
spring.datasource.username=your_username
spring.datasource.password=your_password
# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# Server
server.port=8080
```

### 3. Frontend Setup

```bash
cd gestion-salaries-frontend

# Install dependencies
npm install

# Start development server
npm start
```

**Frontend Configuration**:

- API Base URL: `http://localhost:8080/api`
- Development Port: 3000

## 📁 Project Structure

```
gestion-salaries/
├── gestion-salaries-backend/          # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/netcon/gestion_salaries/
│   │       ├── controller/            # REST Controllers
│   │       ├── service/               # Business Logic
│   │       ├── dao/                   # Data Access Objects
│   │       ├── entity/                # JPA Entities
│   │       └── dto/                   # Data Transfer Objects
│   ├── src/main/resources/
│   │   ├── application.properties     # Configuration
│   │   └── insert_test_data.sql      # Test Data
│   └── pom.xml                       # Maven Dependencies
│
├── gestion-salaries-frontend/         # React Frontend
│   ├── src/
│   │   ├── components/                # React Components
│   │   ├── services/                  # API Services
│   │   ├── utils/                     # Utility Functions
│   │   └── App.js                     # Main App Component
│   ├── package.json                   # Node.js Dependencies
│   └── public/                        # Static Assets
│
└── README.md                          # This file
```

## 🔧 API Endpoints

### Employee Management

- `GET /api/employes` - Get all employees
- `GET /api/employes/{id}` - Get employee by ID
- `POST /api/employes` - Create new employee
- `PUT /api/employes/{id}` - Update employee
- `DELETE /api/employes/{id}` - Delete employee

### Attestation Management

- `GET /api/attestations` - Get all attestations
- `GET /api/attestations/{id}` - Get attestation by ID
- `POST /api/attestations` - Generate new attestation
- `DELETE /api/attestations/{id}` - Delete attestation

## 🧪 Testing

### Backend Tests

```bash
cd gestion-salaries-backend
.\mvnw.cmd test
```

### Frontend Tests

```bash
cd gestion-salaries-frontend
npm test
```

For detailed testing information, see [TEST_README.md](TEST_README.md).

## 🚀 Deployment

### Backend Deployment

```bash
# Build JAR file
.\mvnw.cmd clean package

# Run JAR file
java -jar target/gestion-salaries-backend-1.0.0.jar
```

### Frontend Deployment

```bash
# Build production version
npm run build

# Serve build folder
npx serve -s build
```

## 📱 Features

### Employee Management

- ✅ Add new employees
- ✅ View employee list
- ✅ Update employee information
- ✅ Delete employees
- ✅ Search and filter employees

### Attestation Generation

- ✅ Generate work certificates
- ✅ Generate salary certificates
- ✅ Generate employment certificates
- ✅ Download PDF attestations
- ✅ Track attestation history

### User Interface

- ✅ Modern Material-UI design
- ✅ Responsive layout
- ✅ Intuitive navigation
- ✅ Real-time data updates

## 🔒 Security Features

- Input validation and sanitization
- SQL injection prevention
- CORS configuration
- Error handling and logging

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
    - Verify MySQL is running
    - Check database credentials in `application.properties`
    - Ensure database exists

2. **Port Already in Use**
    - Change port in `application.properties`
    - Kill process using the port

3. **Frontend Build Errors**
    - Clear `node_modules` and reinstall
    - Check Node.js version compatibility

### Logs

- Backend logs: Check console output
- Frontend logs: Check browser console

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:

- Create an issue in the repository
- Contact the development team

---

**Last Updated**: December 2024
**Version**: 1.0.0
**Status**: Production Ready ✅

INSERT INTO parametre (type, value, label) VALUES ('ATTESTATION', 'TRAVAIL', 'Attestation Travail');
INSERT INTO parametre (type, value, label) VALUES ('ATTESTATION', 'SALAIRE', 'Attestation Salaire');
INSERT INTO parametre (type, value, label) VALUES ('ATTESTATION', 'TITULARISATION', 'Attestation titularisation');
