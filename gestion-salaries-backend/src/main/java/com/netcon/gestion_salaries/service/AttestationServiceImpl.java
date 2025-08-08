package com.netcon.gestion_salaries.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttestationServiceImpl implements IAttestationService {

    private final IAttestationDao attestationDao;

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
    public AttestationDto generateAndSave(AttestationDto attestation) throws Exception {
        EmployeDto employe = attestation.getEmploye();
        String content = generateContent(employe, attestation.getTypeAttestation());

        attestation = save(attestation);
        String filePath = generatePdf(attestation, content);

        attestation.setCheminFichier(filePath);
        return attestationDao.save(attestation);
    }

    private String generateContent(EmployeDto e, String type) {
        if ("Travail".equalsIgnoreCase(type)) {
            return String.format(
                    "Nous soussignés NETCON CONSULTING, certifions que Monsieur/Madame %s %s, titulaire de la CIN %s, est employé(e) au poste de %s depuis le %s au sein du service %s.",
                    e.getNom(), e.getPrenom(), e.getCin(), e.getPoste(), e.getDateEmbauche(), e.getService());
        } else {
            return String.format(
                    "Nous certifions que Monsieur/Madame %s %s perçoit un salaire mensuel brut en tant que %s depuis le %s.",
                    e.getNom(), e.getPrenom(), e.getPoste(), e.getDateEmbauche());
        }
    }

    private String generatePdf(AttestationDto a, String content) throws IOException, DocumentException {
        String dir = "pdfs/";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dir));

        String fileName = dir + "attestation_" + a.getId() + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        document.add(new Paragraph(content));
        document.close();

        return fileName;
    }
}
