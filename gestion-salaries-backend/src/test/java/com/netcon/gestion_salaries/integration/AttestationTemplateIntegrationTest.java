package com.netcon.gestion_salaries.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcon.gestion_salaries.records.AttestationTypeRequest;
import com.netcon.gestion_salaries.records.AttestationTypeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class AttestationTemplateIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private static final String SAMPLE_JRXML = """
        <?xml version="1.0" encoding="UTF-8"?>
        <jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports
                      http://jasperreports.sourceforge.net/xsd/jasperreport.xsd"
                      name="TestTemplate" pageWidth="595" pageHeight="842">
            <parameter name="employeId" class="java.lang.Long"/>
            <query language="sql">
                <![CDATA[SELECT nom, prenom FROM employe WHERE id = $P{employeId}]]>
            </query>
            <field name="nom" class="java.lang.String"/>
            <field name="prenom" class="java.lang.String"/>
        </jasperReport>
        """;
    
    @Test
    void testCreateAndRetrieveAttestationType() throws Exception {
        AttestationTypeRequest request = new AttestationTypeRequest("TEST_TEMPLATE", SAMPLE_JRXML);
        
        // Create template
        String response = mockMvc.perform(post("/api/parametres/parametrage/attestations/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("TEST_TEMPLATE"))
                .andExpect(jsonPath("$.type").value("ATTESTATION"))
                .andExpect(jsonPath("$.label").value("Attestation Test_template"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        AttestationTypeResponse createdTemplate = objectMapper.readValue(response, AttestationTypeResponse.class);
        
        // Retrieve all templates
        mockMvc.perform(get("/api/parametres/parametrage/attestations/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.value == 'TEST_TEMPLATE')]").exists());
        
        // Update template
        AttestationTypeRequest updateRequest = new AttestationTypeRequest("TEST_TEMPLATE_UPDATED", SAMPLE_JRXML);
        mockMvc.perform(put("/api/parametres/parametrage/attestations/types/" + createdTemplate.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("TEST_TEMPLATE_UPDATED"));
        
        // Delete template
        mockMvc.perform(delete("/api/parametres/parametrage/attestations/types/" + createdTemplate.id()))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetAttestationTypesPublicAccess() throws Exception {
        // This should be accessible without authentication
        mockMvc.perform(get("/api/parametres/parametrage/attestations/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void testCreateAttestationTypeValidation() throws Exception {
        AttestationTypeRequest request = new AttestationTypeRequest("", "invalid jrxml");
        
        mockMvc.perform(post("/api/parametres/parametrage/attestations/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}