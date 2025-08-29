# Migration from OpenPDF to Jasper Reports

This document describes the migration from OpenPDF to Jasper Reports in the Gestion Salaries project.

## Overview

The project has been migrated from using OpenPDF (a fork of iText) to Jasper Reports for PDF generation. This migration provides better report design capabilities, improved maintainability, and more professional-looking output.

## Changes Made

### 1. Dependencies Updated

**Before (OpenPDF):**
```xml
<dependency>
    <groupId>com.github.librepdf</groupId>
    <artifactId>openpdf</artifactId>
    <version>1.3.29</version>
</dependency>
```

**After (Jasper Reports):**
```xml
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports</artifactId>
    <version>6.20.6</version>
</dependency>
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports-fonts</artifactId>
    <version>6.20.6</version>
</dependency>
```

### 2. Code Changes

#### AttestationServiceImpl.java

**Before:** Used OpenPDF classes (`com.lowagie.text.*`, `com.lowagie.text.pdf.*`)
**After:** Uses Jasper Reports classes (`net.sf.jasperreports.engine.*`)

**Key Changes:**
- Replaced `Document`, `PdfWriter`, `PdfPTable`, etc. with Jasper Reports API
- Replaced manual PDF building with template-based approach
- Added proper error handling and logging
- Simplified code structure

#### New Files Created

1. **`reports/attestation_template.jrxml`** - Jasper Reports template file
2. **`JasperReportService.java`** - Utility service for Jasper Reports operations

### 3. Template-Based Approach

Instead of building PDFs programmatically with OpenPDF, we now use a Jasper Reports template (`.jrxml` file) that defines the layout, styling, and data binding. This approach provides:

- **Separation of Concerns:** Design and logic are separated
- **Easier Maintenance:** Changes to layout don't require code changes
- **Better Design Control:** Professional report design tools available
- **Reusability:** Templates can be reused across different reports

## Benefits of Jasper Reports

1. **Professional Design:** Better typography, layout, and styling options
2. **Template-Based:** Easier to maintain and modify reports
3. **Rich Features:** Support for charts, images, subreports, etc.
4. **Multiple Output Formats:** PDF, HTML, Excel, Word, etc.
5. **Better Performance:** Optimized rendering engine
6. **Active Development:** Well-maintained open-source project

## Template Structure

The `attestation_template.jrxml` includes:

- **Title Band:** Company header with logo and company information
- **Page Header:** Report title and reference information
- **Detail Band:** Employee information and content
- **Page Footer:** Signature section, company stamp, and legal information

## Usage

### Basic Usage

```java
// Load and compile template
JasperReport report = jasperReportService.compileReport("reports/attestation_template.jrxml");

// Prepare parameters
Map<String, Object> parameters = new HashMap<>();
parameters.put("typeAttestation", "Travail");
parameters.put("reference", "ATT-123-20241201");
// ... other parameters

// Prepare data
List<Map<String, Object>> dataList = Arrays.asList(employeeData);

// Fill report
JasperPrint jasperPrint = jasperReportService.fillReport(report, parameters, dataList);

// Export to PDF
jasperReportService.exportToPdf(jasperPrint, "output.pdf");
```

### Parameters

The template accepts these parameters:
- `typeAttestation`: Type of attestation ("Travail" or "Salaire")
- `reference`: Reference number for the attestation
- `currentDate`: Current date in formatted string
- `logoPath`: Path to company logo image
- `cachetPath`: Path to company stamp image

### Data Fields

The template expects these data fields:
- `nom`: Employee last name
- `prenom`: Employee first name
- `cin`: Employee CIN number
- `poste`: Employee position
- `service`: Employee service/department
- `dateEmbauche`: Employee hire date

## Migration Steps

1. ✅ Updated `pom.xml` dependencies
2. ✅ Created Jasper Reports template
3. ✅ Refactored `AttestationServiceImpl.java`
4. ✅ Created utility service class
5. ✅ Removed OpenPDF-specific code
6. ✅ Added proper error handling and logging

## Testing

After migration, test the following:

1. **PDF Generation:** Verify that attestation PDFs are generated correctly
2. **Template Rendering:** Check that all elements (logo, text, tables) display properly
3. **Data Binding:** Ensure employee data is correctly populated
4. **Error Handling:** Test error scenarios and verify proper logging

## Troubleshooting

### Common Issues

1. **Template Not Found:** Ensure `attestation_template.jrxml` is in `src/main/resources/reports/`
2. **Image Loading:** Verify that logo and cachet images exist in the `images/` directory
3. **Compilation Errors:** Check template syntax and ensure all required fields are defined

### Debug Tips

1. Enable debug logging for Jasper Reports
2. Check template compilation in the logs
3. Verify data source structure matches template expectations

## Future Enhancements

1. **Additional Report Types:** Create templates for other document types
2. **Dynamic Styling:** Add conditional formatting based on data
3. **Multi-language Support:** Implement internationalization in templates
4. **Report Preview:** Add HTML preview functionality
5. **Batch Processing:** Implement bulk report generation

## Resources

- [Jasper Reports Documentation](https://jasperreports.sourceforge.net/documentation.html)
- [Jasper Reports Studio](https://community.jaspersoft.com/project/jaspersoft-studio) - Visual template designer
- [Jasper Reports Examples](https://github.com/Jaspersoft/jasperreports/tree/master/jasperreports/demo/samples)

## Conclusion

The migration to Jasper Reports provides a more professional, maintainable, and feature-rich solution for PDF generation. The template-based approach makes it easier to modify reports and maintain consistency across the application.
