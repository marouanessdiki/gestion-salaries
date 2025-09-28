# Development Tools & Technologies

This document provides an overview of the tools and technologies used in the Gestion Salaries project development.

## Technology Stack

### Backend Technologies

#### Spring Boot Framework
![Spring Boot Logo](../../assets/tools/spring-boot-logo.png)
**Version**: 3.5.3  
**Usage**: Main backend framework providing auto-configuration, embedded servers, and production-ready features  
**Key Features**: REST API development, dependency injection, security, data access

#### MySQL Database
![MySQL Logo](../../assets/tools/mysql-logo.png)
**Version**: 8.0+  
**Usage**: Primary database for storing employee data, attestations, and system configuration  
**Key Features**: ACID compliance, high performance, scalability, JSON support

#### JasperReports
![JasperReports Logo](../../assets/tools/jasper-logo.png)
**Version**: 7.0.3  
**Usage**: PDF generation engine for creating professional attestation documents  
**Key Features**: Template-based reports, dynamic data binding, multiple output formats

### Frontend Technologies

#### React Framework
![React Logo](../../assets/tools/react-logo.png)
**Version**: 19.1.0  
**Usage**: Frontend UI framework for building responsive user interfaces  
**Key Features**: Component-based architecture, virtual DOM, hooks, context API

#### Material-UI (MUI)
![Material-UI Logo](../../assets/tools/mui-logo.png)
**Version**: 7.2.0  
**Usage**: React component library providing Material Design components  
**Key Features**: Pre-built components, theming system, responsive design, accessibility

#### Axios
![Axios Logo](../../assets/tools/axios-logo.png)
**Version**: 1.10.0  
**Usage**: HTTP client for API communication between frontend and backend  
**Key Features**: Promise-based requests, request/response interceptors, automatic JSON parsing

## Development Tools

### Integrated Development Environments

#### IntelliJ IDEA
![IntelliJ IDEA Logo](../../assets/tools/intellij-logo.png)
**Usage**: Primary IDE for backend development and project management  
**Key Features**: Advanced code completion, debugging tools, Maven integration, database tools

#### Visual Studio Code
![VS Code Logo](../../assets/tools/vscode-logo.png)
**Usage**: Frontend development and general code editing  
**Key Features**: Extensions ecosystem, integrated terminal, Git integration, IntelliSense

### Build Tools

#### Maven
![Maven Logo](../../assets/tools/maven-logo.png)
**Version**: Included with Spring Boot  
**Usage**: Backend project build and dependency management  
**Key Features**: Dependency resolution, build lifecycle, plugin ecosystem

#### npm (Node Package Manager)
**Version**: Included with Node.js  
**Usage**: Frontend package management and build automation  
**Key Features**: Package installation, script execution, version management

## Supporting Libraries

### Backend Libraries

#### Spring Data JPA
**Usage**: Data access layer abstraction and repository pattern implementation  
**Features**: Automatic query generation, pagination, auditing, custom repositories

#### Spring Security
**Usage**: Authentication and authorization framework  
**Features**: Role-based access control, JWT support, CSRF protection, session management

#### MapStruct
**Version**: 1.3.1.Final  
**Usage**: Object mapping between DTOs and entities  
**Features**: Compile-time generation, type safety, performance optimization

#### Lombok
**Version**: 1.18.34  
**Usage**: Code generation to reduce boilerplate  
**Features**: Getters/setters, constructors, builders, logging annotations

### Frontend Libraries

#### React Router
**Version**: 7.7.0  
**Usage**: Client-side routing and navigation  
**Features**: Declarative routing, nested routes, programmatic navigation

#### Emotion
**Versions**: @emotion/react 11.14.0, @emotion/styled 11.14.1  
**Usage**: CSS-in-JS styling solution for React components  
**Features**: Dynamic styling, theme support, performance optimization

#### Testing Libraries
- **@testing-library/react**: 16.3.0 - Component testing utilities
- **@testing-library/jest-dom**: 6.6.3 - Custom Jest matchers
- **@testing-library/user-event**: 13.5.0 - User interaction simulation

## Database Tools

### MySQL Workbench
**Usage**: Database design, administration, and query development  
**Features**: Visual database design, SQL editor, performance monitoring

### H2 Database (Testing)
**Usage**: In-memory database for unit and integration testing  
**Features**: Fast startup, zero configuration, web console

## Version Control

### Git
**Usage**: Source code version control and collaboration  
**Features**: Branch management, merge capabilities, history tracking

### GitHub/GitLab
**Usage**: Remote repository hosting and project collaboration  
**Features**: Issue tracking, pull requests, CI/CD integration

## Deployment Tools

### Maven Wrapper (mvnw)
**Usage**: Consistent Maven version across different environments  
**Features**: No Maven installation required, version consistency

### Spring Boot Maven Plugin
**Usage**: Application packaging and deployment  
**Features**: Fat JAR creation, embedded server, production optimization

## Development Environment Setup

### Prerequisites
- **Java**: 17+ (OpenJDK or Oracle JDK)
- **Node.js**: 18+ with npm
- **MySQL**: 8.0+ server
- **Git**: Latest version

### IDE Configuration
- **IntelliJ IDEA**: Spring Boot plugin, Maven integration, database tools
- **VS Code**: React extensions, ESLint, Prettier, Git integration

### Local Development
```bash
# Backend setup
cd gestion-salaries-backend
./mvnw spring-boot:run

# Frontend setup
cd gestion-salaries-frontend
npm install
npm start
```

## Testing Tools

### Backend Testing
- **JUnit 5**: Unit testing framework
- **Spring Boot Test**: Integration testing utilities
- **Mockito**: Mocking framework for unit tests
- **TestContainers**: Integration testing with real databases

### Frontend Testing
- **Jest**: JavaScript testing framework
- **React Testing Library**: Component testing utilities
- **jsdom**: DOM simulation for testing

## Performance Monitoring

### Spring Boot Actuator
**Usage**: Application monitoring and health checks  
**Features**: Metrics endpoint, health indicators, application info

### Browser DevTools
**Usage**: Frontend performance analysis and debugging  
**Features**: Network monitoring, performance profiling, debugging tools

## Security Tools

### Spring Security
**Usage**: Authentication and authorization  
**Features**: Password encoding, CSRF protection, session management

### HTTPS/SSL
**Usage**: Secure communication in production  
**Features**: Certificate management, encrypted data transmission

## Documentation Tools

### LaTeX
**Usage**: Technical report generation  
**Features**: Professional document formatting, mathematical notation, bibliography

### Markdown
**Usage**: Project documentation and README files  
**Features**: Simple syntax, GitHub integration, easy maintenance

---

*This tools documentation provides a comprehensive overview of the technology stack and development environment used in the Gestion Salaries project.*
