package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttestationServiceImpl implements IAttestationService {

    private final IAttestationDao attestationDao;
    private final IEmployeDao employeDao;
    private final DataSource dataSource;

    @Override
    public List<AttestationDto> findByEmploye(Long employeId) {
        return attestationDao.findByEmployeId(employeId);
    }
    
    @Override
    public AttestationDto save(AttestationDto attestation) {
        attestation.setDateGeneration(LocalDateTime.now());
        return attestationDao.save(attestation);
    }
    
    @Override
    public List<AttestationDto> findAll() {
        return attestationDao.findAll();
    }
    
    @Override
    public AttestationDto findById(Long id) {
        return attestationDao.findById(id);
    }
    
    @Override
    @Transactional
    public AttestationDto generateAndSave(AttestationDto attestation) throws Exception {
        EmployeDto employe = employeDao.findById(attestation.getEmployeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + attestation.getEmployeId()));
        log.info("Found employee: {} {}", employe.getNom(), employe.getPrenom());

        // Save attestation to get ID, but roll back if PDF fails
        AttestationDto saved = save(attestation);
        log.info("Saved attestation with ID: {}", saved.getId());

        try {
            // Ensure pdfs/ directory exists
            String dir = "pdfs/";
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dir));

        // Generate the professional PDF
        String filePath = generateProfessionalPdf(saved, employe, attestation.getTypeAttestation());
            log.info("Generated PDF at path: {}", filePath);

            // Persist the file path
        attestationDao.updateCheminFichier(saved.getId(), filePath);
            log.info("Updated file path in database");

        // Return DTO with updated path
        saved.setCheminFichier(filePath);
        return saved;
        } catch (Exception e) {
            log.error("Error in generateAndSave: {}", e.getMessage(), e);
            // Roll back attestation if PDF generation fails
            attestationDao.deleteById(saved.getId());
            throw new Exception("Attestation generation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        attestationDao.deleteById(id);
    }

    private String generateProfessionalPdf(AttestationDto attestation, EmployeDto employe, String type) throws IOException, JRException {
        String dir = "pdfs/";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dir));
        
        // Create filename: name-of-employee_type-of-attestation_date.pdf
        String employeeName = employe.getNom().replaceAll("[^a-zA-Z0-9]", "") + "-" + employe.getPrenom().replaceAll("[^a-zA-Z0-9]", "");
        String attestationType = type.replaceAll("[^a-zA-Z0-9]", "").replaceAll("\\s+", "-");
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = dir + employeeName + "_" + attestationType + "_" + dateStr + ".pdf";

        log.info("Starting PDF generation for attestation ID: {}", attestation.getId());

        try {
            // Disable ALL JasperReports validations
            System.setProperty("net.sf.jasperreports.xml.validation", "false");
            System.setProperty("net.sf.jasperreports.compiler.xml.validation", "false");
            
            // Load the Jasper template
            log.info("Loading Jasper template...");
            String templatePath = "reports/attestation_template.jrxml";
            if ("Attestation Salaire".equals(type)) {
                templatePath = "reports/attestation_salaire.jrxml";
            } else if ("Attestation Travail".equals(type)) {
                templatePath = "reports/attestation_travail.jrxml";
            } else if ("Attestation Titularisation".equals(type)) {
                templatePath = "reports/attestation_titularisation.jrxml";
            } else if ("Avenant Augmentation Salaire".equals(type)) {
                templatePath = "reports/avenant_augmentation_salaire.jrxml";
            } else if ("Engagement Versement Salaire".equals(type)) {
                templatePath = "reports/engagement_versement_salaire.jrxml";
            }
            
            // Load template content and log first few characters for debugging
            ClassPathResource resource = new ClassPathResource(templatePath);
            if (!resource.exists()) {
                throw new IOException("Template file not found: " + templatePath);
            }
            
            log.info("Loading Jasper template from classpath: {}", templatePath);
            
            // Read and log the beginning of the JRXML for debugging
            try (java.io.InputStream debugStream = resource.getInputStream()) {
                byte[] buffer = new byte[200];
                int bytesRead = debugStream.read(buffer);
                String preview = new String(buffer, 0, bytesRead, "UTF-8");
                log.info("JRXML starts with: {}", preview.replaceAll("\\s+", " "));
            } catch (Exception e) {
                log.warn("Could not read JRXML preview: {}", e.getMessage());
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            log.info("Template compiled successfully");

            // Prepare parameters
            log.info("Preparing parameters...");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("typeAttestation", type);
            parameters.put("reference", "ATT-" + attestation.getId() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            parameters.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            parameters.put("ReportDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            parameters.put("employeId", employe.getId());
            log.info("Added employee ID parameter: {}", employe.getId());
            
            // For SQL-based templates, we need a database connection
            log.info("Preparing database connection...");
            java.sql.Connection connection = null;
            try {
                // Get database connection from Spring's DataSource
                connection = dataSource.getConnection();
                log.info("Database connection established");

                // Fill the report with SQL data source
                log.info("Filling report with SQL data source...");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
                log.info("Report filled successfully");

                // Export to PDF
                log.info("Exporting to PDF file: {}", fileName);
                JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);

                log.info("PDF generated successfully: {}", fileName);
                return fileName;

            } finally {
                if (connection != null) {
                    connection.close();
                    log.info("Database connection closed");
                }
            }

        } catch (Exception e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            
            // Enhanced error logging
            if (e.getCause() != null) {
                log.error("Root cause class: {}", e.getCause().getClass().getName());
                log.error("Root cause message: {}", e.getCause().getMessage());
            }
            
            // Create a debug dump of the JRXML file
            try {
                String templatePath = "reports/attestation_template.jrxml";
                if ("Attestation Salaire".equals(type)) {
                    templatePath = "reports/attestation_salaire.jrxml";
                }
                ClassPathResource resource = new ClassPathResource(templatePath);
                if (resource.exists()) {
                    try (java.io.InputStream is = resource.getInputStream()) {
                        String debugFile = "pdfs/_debug_attestation_error.jrxml";
                        java.nio.file.Files.copy(is, java.nio.file.Paths.get(debugFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        log.error("JRXML failed to load. A copy was dumped to {}", debugFile);
                    }
                }
            } catch (Exception debugEx) {
                log.warn("Could not create debug dump: {}", debugEx.getMessage());
            }
            
            throw new JRException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }
}
