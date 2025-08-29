package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String fileName = dir + "attestation_" + attestation.getId() + ".pdf";

        log.info("Starting PDF generation for attestation ID: {}", attestation.getId());

        try {
            // Load the Jasper template
            log.info("Loading Jasper template...");
            ClassPathResource resource = new ClassPathResource("reports/attestation_template.jrxml");
            if (!resource.exists()) {
                throw new IOException("Template file not found: reports/attestation_template.jrxml");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            log.info("Template compiled successfully");

            // Prepare parameters
            log.info("Preparing parameters...");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("typeAttestation", type);
            parameters.put("reference", "ATT-" + attestation.getId() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            parameters.put("currentDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            // Skip images for simple test
            log.info("Skipping images for simple test");

            // Prepare data source
            log.info("Preparing employee data...");
            Map<String, Object> employeeData = new HashMap<>();
            employeeData.put("nom", employe.getNom() != null ? employe.getNom() : "");
            employeeData.put("prenom", employe.getPrenom() != null ? employe.getPrenom() : "");
            employeeData.put("cin", employe.getCin() != null ? employe.getCin() : "");
            employeeData.put("poste", employe.getPoste() != null ? employe.getPoste() : "");
            employeeData.put("service", employe.getService() != null ? employe.getService() : "");
            employeeData.put("dateEmbauche", employe.getDateEmbauche() != null ? 
                employe.getDateEmbauche().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");

            List<Map<String, Object>> dataList = Arrays.asList(employeeData);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            log.info("Data source prepared with {} records", dataList.size());

            // Fill the report
            log.info("Filling report...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            log.info("Report filled successfully");

            // Export to PDF
            log.info("Exporting to PDF file: {}", fileName);
            JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);

            log.info("PDF generated successfully: {}", fileName);
            return fileName;

        } catch (Exception e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
            throw new JRException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }
}
