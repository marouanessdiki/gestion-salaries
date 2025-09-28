# UML Diagrams Documentation

This directory contains PlantUML source files for all UML diagrams used in the Gestion Salaries project.

## Diagram Files

### 1. Use Case Diagram
**File**: `use_case.puml`  
**Purpose**: Shows the interaction between different user roles and system functionalities  
**Key Elements**:
- Actors: HR Manager, Admin
- Use Cases: Employee Management, Attestation Generation, Template Management
- Relationships: Extends, Includes, User interactions

### 2. Entity Relationship Diagram
**File**: `data_er.puml`  
**Purpose**: Illustrates the database schema and relationships between entities  
**Key Elements**:
- Entities: Employe, Attestation, AttestationTemplate, Hr, Parametre
- Relationships: One-to-Many, Many-to-One
- Attributes: Primary keys, foreign keys, constraints

### 3. Sequence Diagram
**File**: `sequence.puml`  
**Purpose**: Shows the detailed flow of the attestation generation process  
**Key Elements**:
- Participants: HR User, Frontend, Controller, Service, Database
- Messages: API calls, data validation, PDF generation
- Process Flow: Complete attestation generation workflow

## Prerequisites

To generate diagrams from PlantUML source files, you need:

1. **Java Runtime Environment** (JRE 8 or higher)
2. **PlantUML JAR file** (download from [plantuml.com](http://plantuml.com/))

## Installation

### Option 1: Download PlantUML JAR
```bash
# Download PlantUML JAR file
wget http://sourceforge.net/projects/plantuml/files/plantuml.jar/download -O plantuml.jar

# Make it executable
chmod +x plantuml.jar
```

### Option 2: Install via Package Manager
```bash
# Ubuntu/Debian
sudo apt-get install plantuml

# macOS (with Homebrew)
brew install plantuml

# Windows (with Chocolatey)
choco install plantuml
```

## Generation Commands

### Generate All Diagrams
```bash
# From the project root directory
java -jar plantuml.jar docs/uml/*.puml

# Or if plantuml is in PATH
plantuml docs/uml/*.puml
```

### Generate Individual Diagrams
```bash
# Use Case Diagram
plantuml docs/uml/use_case.puml

# Entity Relationship Diagram
plantuml docs/uml/data_er.puml

# Sequence Diagram
plantuml docs/uml/sequence.puml
```

### Generate with Specific Output Format
```bash
# Generate as SVG (default)
plantuml -tsvg docs/uml/use_case.puml

# Generate as PNG
plantuml -tpng docs/uml/use_case.puml

# Generate as PDF
plantuml -tpdf docs/uml/use_case.puml
```

## Output Files

Generated diagrams will be created in the same directory as the source files:

- `use_case.svg` - Use case diagram
- `data_er.svg` - Entity relationship diagram  
- `sequence.svg` - Sequence diagram

## Integration with Documentation

The generated diagrams are automatically integrated into the project documentation:

- **Use Case Diagram**: Referenced in `docs/conception/diagrams.md`
- **ER Diagram**: Used for database design documentation
- **Sequence Diagram**: Shows attestation generation process flow

## Customization

### Modifying Diagrams

1. Edit the `.puml` source files using any text editor
2. Regenerate using the commands above
3. Update documentation references if needed

### Adding New Diagrams

1. Create new `.puml` file in this directory
2. Follow PlantUML syntax conventions
3. Add generation commands to this README
4. Update documentation to reference new diagrams

### Theme Customization

PlantUML supports various themes. To change the appearance:

```plantuml
@startuml
!theme plain
!theme spacelab
!theme superhero
!theme sketchy

' Your diagram content here

@enduml
```

## Troubleshooting

### Common Issues

1. **Java Not Found**
   ```
   Error: Could not find or load main class plantuml
   ```
   **Solution**: Ensure Java is installed and in PATH

2. **File Not Found**
   ```
   Error: Cannot find file docs/uml/use_case.puml
   ```
   **Solution**: Run commands from project root directory

3. **Permission Denied**
   ```
   Error: Permission denied
   ```
   **Solution**: Make PlantUML JAR executable: `chmod +x plantuml.jar`

### Alternative Generation Methods

If PlantUML installation is not possible, use online tools:

1. **PlantUML Online Server**: [http://www.plantuml.com/plantuml/uml/](http://www.plantuml.com/plantuml/uml/)
2. **VS Code Extension**: PlantUML extension for VS Code
3. **IntelliJ Plugin**: PlantUML integration plugin

## File Structure

```
docs/uml/
├── README.md           # This documentation file
├── use_case.puml       # Use case diagram source
├── data_er.puml        # Entity relationship diagram source
├── sequence.puml       # Sequence diagram source
├── use_case.svg        # Generated use case diagram
├── data_er.svg         # Generated ER diagram
└── sequence.svg        # Generated sequence diagram
```

## Version Control

- Source files (`.puml`) are version controlled
- Generated files (`.svg`, `.png`) can be regenerated and are optional in version control
- Update diagrams when system architecture changes

## Best Practices

1. **Keep source files simple**: Use clear, readable PlantUML syntax
2. **Document relationships**: Add notes explaining complex relationships
3. **Version control**: Commit changes to source files, not generated images
4. **Regular updates**: Keep diagrams synchronized with code changes
5. **Consistent styling**: Use consistent colors and formatting across diagrams

---

*This documentation ensures that UML diagrams can be easily regenerated and maintained throughout the project lifecycle.*
