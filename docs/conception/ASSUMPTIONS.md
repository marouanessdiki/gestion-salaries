# Project Assumptions

This document outlines all assumptions made during the Gestion Salaries project development and implementation.

## Technical Assumptions

### 1. PlantUML Installation
**Assumption**: PlantUML is not installed on the development environment  
**Impact**: UML diagrams are provided as SVG placeholders instead of generated from PlantUML source  
**Mitigation**: Created static SVG versions of all UML diagrams with clear instructions for regeneration  
**Regeneration Instructions**: 
```bash
# Install PlantUML (requires Java)
# Then run:
plantuml docs/uml/use_case.puml
plantuml docs/uml/data_er.puml
plantuml docs/uml/sequence.puml
```

### 2. Python Environment
**Assumption**: Python 3.x is available for Gantt chart generation  
**Impact**: Gantt chart generator script created in Python  
**Dependencies**: PyYAML for YAML parsing  
**Installation**: `pip install pyyaml` (if not already available)

### 3. LaTeX Compilation
**Assumption**: LaTeX environment (MiKTeX/TeX Live) is properly configured  
**Impact**: Report compilation may require specific LaTeX packages  
**Known Issues**: TikZ diagrams, font encoding, image handling  
**Mitigation**: Provided compilation guide and fixed common LaTeX errors

## Project Methodology Assumptions

### 4. Waterfall Methodology
**Assumption**: Sequential development approach is suitable for this project scope  
**Justification**: Well-defined requirements, individual development, supervisor guidance  
**Impact**: Structured phase-based development with clear milestones  
**Alternative**: Could have used Agile/Scrum for more flexibility

### 5. Individual Development Model
**Assumption**: Single developer can effectively handle full-stack development  
**Justification**: Project scope is manageable, supervisor provides guidance  
**Impact**: Consistent code style, complete system knowledge, efficient communication  
**Risk Mitigation**: Regular supervisor meetings and phase reviews

### 6. Supervisor Availability
**Assumption**: Supervisor (M. Ahmed ASSIMI) is available for weekly meetings  
**Impact**: Weekly progress reviews and technical guidance  
**Fallback**: Independent development with documented decision rationale

## Technical Architecture Assumptions

### 7. Database Choice
**Assumption**: MySQL 8.0+ is the preferred database solution  
**Justification**: Production-ready, ACID compliance, good Spring Boot integration  
**Alternative**: PostgreSQL, H2 (for development), Oracle

### 8. Frontend Framework Choice
**Assumption**: React with Material-UI is suitable for the UI requirements  
**Justification**: Component-based architecture, rich component library, modern development practices  
**Alternative**: Vue.js, Angular, vanilla JavaScript

### 9. PDF Generation Approach
**Assumption**: JasperReports is the best solution for PDF generation  
**Justification**: Template-based, professional output, Spring Boot integration  
**Alternative**: iText, Flying Saucer, wkhtmltopdf

## Business Assumptions

### 10. User Roles
**Assumption**: Two primary user roles (Admin, HR) are sufficient  
**Impact**: Role-based access control implementation  
**Future Consideration**: Additional roles may be needed (Manager, Employee self-service)

### 11. Attestation Types
**Assumption**: Five attestation types cover all business requirements  
**Types**: Work, Salary, Tenure, Salary Increase, Salary Payment Commitment  
**Future Consideration**: Additional attestation types may be requested

### 12. Company Information
**Assumption**: NETCON CONSULTING company information is accurate and up-to-date  
**Source**: Company website (https://netconconsulting.com/)  
**Impact**: Company details used in attestations and documentation  
**Verification**: Information verified from official company sources

## Development Environment Assumptions

### 13. Operating System
**Assumption**: Development environment supports Windows PowerShell commands  
**Impact**: Directory creation and file operations use PowerShell syntax  
**Alternative**: Bash/Linux commands for Unix-based systems

### 14. Network Access
**Assumption**: Internet access available for dependency downloads  
**Impact**: Maven and npm can download packages during build  
**Fallback**: Offline package repositories or cached dependencies

### 15. Resource Availability
**Assumption**: Sufficient system resources for development tools  
**Impact**: IntelliJ IDEA, VS Code, MySQL, and browsers can run simultaneously  
**Minimum Requirements**: 8GB RAM, 4-core CPU, 10GB free disk space

## Security Assumptions

### 16. Authentication Approach
**Assumption**: Simple username/password authentication is sufficient  
**Impact**: No advanced authentication (OAuth, SSO, MFA) implemented  
**Future Consideration**: Enhanced security features may be required

### 17. Data Privacy
**Assumption**: Employee data privacy requirements are standard  
**Impact**: Basic data protection measures implemented  
**Compliance**: Consider GDPR, local data protection laws

### 18. File Storage
**Assumption**: Local file system storage is acceptable for PDF files  
**Impact**: PDFs stored in application file system  
**Alternative**: Cloud storage (AWS S3, Azure Blob), database storage

## Performance Assumptions

### 19. User Load
**Assumption**: System will handle moderate user load (10-50 concurrent users)  
**Impact**: Standard Spring Boot configuration sufficient  
**Scaling**: Horizontal scaling may be needed for higher loads

### 20. Data Volume
**Assumption**: Employee database will contain hundreds to thousands of records  
**Impact**: Standard database indexing and query optimization  
**Future Consideration**: Large-scale data optimization for enterprise use

### 21. PDF Generation Performance
**Assumption**: JasperReports can handle concurrent PDF generation  
**Impact**: Synchronous PDF generation implementation  
**Optimization**: Async processing for high-volume scenarios

## Deployment Assumptions

### 22. Production Environment
**Assumption**: Traditional server deployment is acceptable  
**Impact**: JAR file deployment with embedded Tomcat  
**Alternative**: Container deployment (Docker), cloud platforms

### 23. Database Hosting
**Assumption**: MySQL server can be hosted on same machine or network  
**Impact**: Local network database connection configuration  
**Alternative**: Managed database services (AWS RDS, Azure Database)

### 24. Backup Strategy
**Assumption**: Standard database backup procedures are sufficient  
**Impact**: Manual backup documentation provided  
**Production**: Automated backup solutions recommended

## Documentation Assumptions

### 25. Documentation Format
**Assumption**: Markdown and LaTeX are suitable documentation formats  
**Impact**: Documentation created in multiple formats  
**Accessibility**: HTML versions available for web viewing

### 26. Language Requirements
**Assumption**: English documentation is acceptable  
**Impact**: All documentation written in English  
**Localization**: French/Arabic documentation may be required for local use

### 27. Technical Level
**Assumption**: Documentation targets technical and non-technical audiences  
**Impact**: Multiple documentation levels (overview, technical, user guides)  
**Adaptation**: Documentation can be customized for specific audiences

## Validation and Testing Assumptions

### 28. Test Coverage
**Assumption**: Unit and integration tests provide adequate coverage  
**Impact**: Comprehensive test suite implemented  
**Limitation**: End-to-end testing may be limited by environment constraints

### 29. Browser Compatibility
**Assumption**: Modern browsers (Chrome, Firefox, Safari, Edge) are sufficient  
**Impact**: React application targets modern browser features  
**Legacy**: Internet Explorer support not implemented

### 30. Mobile Responsiveness
**Assumption**: Material-UI responsive design covers mobile requirements  
**Impact**: Mobile-first responsive design implementation  
**Testing**: Limited mobile device testing during development

## Risk Mitigation

### High-Risk Assumptions
1. **PlantUML Availability**: Mitigated with SVG placeholders
2. **Supervisor Availability**: Mitigated with documented decision process
3. **Technical Complexity**: Mitigated with proven technology stack

### Medium-Risk Assumptions
1. **Performance Requirements**: Can be addressed with optimization
2. **Security Requirements**: Can be enhanced with additional measures
3. **Scalability Needs**: Can be addressed with architectural changes

### Low-Risk Assumptions
1. **Documentation Formats**: Easily adaptable
2. **Development Tools**: Widely available alternatives
3. **Browser Compatibility**: Modern standard approach

---

*These assumptions were documented to ensure transparency and provide guidance for future development and deployment decisions.*
