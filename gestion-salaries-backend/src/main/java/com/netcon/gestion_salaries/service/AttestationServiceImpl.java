package com.netcon.gestion_salaries.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
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
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create and persist once to obtain ID
        AttestationDto saved = save(attestation);

        // Generate the professional PDF
        String filePath = generateProfessionalPdf(saved, employe, attestation.getTypeAttestation());

        // Persist the file path without inserting a new record
        attestationDao.updateCheminFichier(saved.getId(), filePath);

        // Return DTO with updated path
        saved.setCheminFichier(filePath);
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        attestationDao.deleteById(id);
    }

    private String generateProfessionalPdf(AttestationDto attestation, EmployeDto employe, String type) throws IOException, DocumentException {
        String dir = "pdfs/";
        java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dir));
        String fileName = dir + "attestation_" + attestation.getId() + ".pdf";

        Document document = new Document(PageSize.A4, 50, 50, 80, 50);
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        // Add header
        addHeader(document);
        
        // Add title
        addTitle(document, type);
        
        // Add reference and date
        addReference(document, attestation);
        
        // Add content
        addContent(document, employe, type);
        
        // Add employee details table
        addEmployeeDetailsTable(document, employe);
        
        // Add footer text
        addFooterText(document);
        
        // Add signature section
        addSignatureSection(document);
        
        // Add legal footer
        addLegalFooter(document);

        document.close();
        return fileName;
    }

    private void addHeader(Document document) throws DocumentException {
        // Company header with logo
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{20, 80});
        
        // Logo cell
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        logoCell.setFixedHeight(60);
        
        try {
            // Try to load logo image
            Image logo = Image.getInstance("images/logo.png");
            logo.scaleToFit(50, 50);
            logoCell.addElement(logo);
        } catch (Exception e) {
            // Fallback to text logo if image not found
            Font logoFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            Paragraph logoText = new Paragraph("NETCON\nCONSULTING", logoFont);
            logoText.setAlignment(Element.ALIGN_CENTER);
            logoCell.addElement(logoText);
        }
        
        // Company info cell
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setPaddingLeft(20);
        
        Font companyFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font infoFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        
        Paragraph companyName = new Paragraph("NETCON CONSULTING", companyFont);
        Paragraph companyInfo = new Paragraph("Siège Social : 05 RUE DIXMUDE, 1 ERE ETAGE APPT 2, CASABLANCA | netconconsulting.com", infoFont);
        
        infoCell.addElement(companyName);
        infoCell.addElement(companyInfo);
        
        headerTable.addCell(logoCell);
        headerTable.addCell(infoCell);
        
        document.add(headerTable);
        
        // Add separator line
        Paragraph separator = new Paragraph("_".repeat(80));
        separator.setAlignment(Element.ALIGN_CENTER);
        separator.setSpacingAfter(20);
        document.add(separator);
    }

    private void addTitle(Document document, String type) throws DocumentException {
        String title = "Travail".equalsIgnoreCase(type) ? "ATTESTATION DE TRAVAIL" : "ATTESTATION DE SALAIRE";
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph titleParagraph = new Paragraph(title, titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(10);
        document.add(titleParagraph);
    }

    private void addReference(Document document, AttestationDto attestation) throws DocumentException {
        String reference = "ATT-" + attestation.getId() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        Font refFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        Paragraph refParagraph = new Paragraph("Réf : " + reference + " — Date : " + currentDate, refFont);
        refParagraph.setAlignment(Element.ALIGN_CENTER);
        refParagraph.setSpacingAfter(20);
        document.add(refParagraph);
    }

    private void addContent(Document document, EmployeDto employe, String type) throws DocumentException {
        Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
        
        String content;
        if ("Travail".equalsIgnoreCase(type)) {
            content = String.format("Nous, soussignés NETCON CONSULTING, certifions que %s %s, titulaire de la CIN %s, est employé(e) en qualité de %s depuis le %s au sein du service %s.",
                    employe.getNom(), employe.getPrenom(), employe.getCin(), employe.getPoste(), 
                    employe.getDateEmbauche().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), employe.getService());
        } else {
            content = String.format("Nous certifions que %s %s perçoit un salaire mensuel brut en tant que %s depuis le %s.",
                    employe.getNom(), employe.getPrenom(), employe.getPoste(), 
                    employe.getDateEmbauche().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        
        Paragraph contentParagraph = new Paragraph(content, contentFont);
        contentParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        contentParagraph.setSpacingAfter(20);
        document.add(contentParagraph);
    }

    private void addEmployeeDetailsTable(Document document, EmployeDto employe) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{40, 60});
        
        Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
        
        // Add employee details
        addTableRow(table, "Nom complet", employe.getNom() + " " + employe.getPrenom(), headerFont, dataFont);
        addTableRow(table, "CIN", employe.getCin(), headerFont, dataFont);
        addTableRow(table, "Poste", employe.getPoste(), headerFont, dataFont);
        addTableRow(table, "Service", employe.getService(), headerFont, dataFont);
        addTableRow(table, "Date d'embauche", employe.getDateEmbauche().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), headerFont, dataFont);
        
        table.setSpacingAfter(20);
        document.add(table);
    }

    private void addTableRow(PdfPTable table, String label, String value, Font headerFont, Font dataFont) {
        PdfPCell labelCell = new PdfPCell(new Paragraph(label, headerFont));
        labelCell.setPadding(8);
        labelCell.setBorder(Rectangle.BOX);
        
        PdfPCell valueCell = new PdfPCell(new Paragraph(value, dataFont));
        valueCell.setPadding(8);
        valueCell.setBorder(Rectangle.BOX);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addFooterText(Document document) throws DocumentException {
        Font footerFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
        Paragraph footerParagraph = new Paragraph("La présente attestation est délivrée à l'intéressé(e) pour servir et valoir ce que de droit.", footerFont);
        footerParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        footerParagraph.setSpacingAfter(30);
        document.add(footerParagraph);
    }

    private void addSignatureSection(Document document) throws DocumentException {
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setWidths(new float[]{50, 50});
        
        // Signature box
        PdfPCell signatureCell = new PdfPCell();
        signatureCell.setBorder(Rectangle.BOX);
        signatureCell.setPadding(15);
        signatureCell.setFixedHeight(120);
        
        Font signatureFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        Paragraph signatureText = new Paragraph();
        signatureText.add(new Chunk("Fait à Casablanca, le " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), signatureFont));
        signatureText.add(new Chunk("\n\nLe/La Responsable\n\n", signatureFont));
        
        Font companyFont = new Font(Font.HELVETICA, 11, Font.BOLD);
        signatureText.add(new Chunk("NETCON CONSULTING\n", companyFont));
        signatureText.add(new Chunk("Direction Générale", signatureFont));
        
        signatureCell.addElement(signatureText);
        
        // Stamp box with cachet image
        PdfPCell stampCell = new PdfPCell();
        stampCell.setBorder(Rectangle.BOX);
        stampCell.setPadding(15);
        stampCell.setFixedHeight(120);
        stampCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        stampCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        try {
            // Try to load cachet image
            Image cachet = Image.getInstance("images/cachet.png");
            cachet.scaleToFit(80, 80);
            cachet.setAlignment(Element.ALIGN_CENTER);
            stampCell.addElement(cachet);
            
            // Add text below the image
            Font stampFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
            Paragraph stampText = new Paragraph("Cachet de l'entreprise", stampFont);
            stampText.setAlignment(Element.ALIGN_CENTER);
            stampCell.addElement(stampText);
        } catch (Exception e) {
            // Fallback to text stamp if image not found
            Font stampFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            Paragraph stampText = new Paragraph("Cachet de l'entreprise\n\nNETCON\nCONSULTING", stampFont);
            stampText.setAlignment(Element.ALIGN_CENTER);
            stampCell.addElement(stampText);
        }
        
        signatureTable.addCell(signatureCell);
        signatureTable.addCell(stampCell);
        
        signatureTable.setSpacingAfter(50);
        document.add(signatureTable);
    }

    private void addLegalFooter(Document document) throws DocumentException {
        Font legalFont = new Font(Font.HELVETICA, 9, Font.NORMAL);
        
        PdfPTable legalTable = new PdfPTable(5);
        legalTable.setWidthPercentage(100);
        legalTable.setWidths(new float[]{20, 20, 20, 20, 20});
        
        String[] legalInfo = {"RC : 530199", "CNSS : 4021653", "IF : 51699164", "PTE : 34263449", "ICE : 002996175000014"};
        
        for (String info : legalInfo) {
            PdfPCell cell = new PdfPCell(new Paragraph(info, legalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            legalTable.addCell(cell);
        }
        
        document.add(legalTable);
    }
}
