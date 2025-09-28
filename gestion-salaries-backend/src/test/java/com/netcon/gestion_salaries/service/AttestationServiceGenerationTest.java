package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.entity.AttestationTemplate;
import com.netcon.gestion_salaries.service.inteface.IAttestationTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttestationServiceGenerationTest {
    
    @Mock
    private IAttestationTemplateService attestationTemplateService;
    
    @Mock
    private DataSource dataSource;
    
    @Mock
    private Connection connection;
    
    @InjectMocks
    private AttestationServiceImpl attestationService;
    
    private static final String SAMPLE_JRXML = """
        <?xml version="1.0" encoding="UTF-8"?>
        <jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports
                      http://jasperreports.sourceforge.net/xsd/jasperreport.xsd"
                      name="TestTemplate" pageWidth="595" pageHeight="842">
            <parameter name="employeId" class="java.lang.Long"/>
            <query language="sql">
                <![CDATA[SELECT 'Test' as nom, 'User' as prenom FROM dual WHERE 1=1]]>
            </query>
            <field name="nom" class="java.lang.String"/>
            <field name="prenom" class="java.lang.String"/>
            <detail>
                <band height="50">
                    <textField>
                        <reportElement x="0" y="0" width="200" height="20"/>
                        <textFieldExpression><![CDATA[$F{nom}]]></textFieldExpression>
                    </textField>
                </band>
            </detail>
        </jasperReport>
        """;
    
    @BeforeEach
    void setUp() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
    }
    
    @Test
    void testGenerateAttestationSuccess() throws Exception {
        // Given
        AttestationTemplate template = new AttestationTemplate();
        template.setId(1L);
        template.setName("TEST_TEMPLATE");
        template.setJrxml(SAMPLE_JRXML);
        template.setUpdatedAt(LocalDateTime.now());
        
        when(attestationTemplateService.findByName("TEST_TEMPLATE")).thenReturn(template);
        
        Map<String, Object> params = new HashMap<>();
        params.put("typeAttestation", "Test");
        
        // When & Then - Should not throw exception for valid JRXML
        assertDoesNotThrow(() -> {
            byte[] result = attestationService.generateAttestation("TEST_TEMPLATE", 1L, params);
            assertNotNull(result);
            assertTrue(result.length > 0);
        });
        
        verify(attestationTemplateService).findByName("TEST_TEMPLATE");
    }
    
    @Test
    void testGenerateAttestationTemplateNotFound() {
        // Given
        when(attestationTemplateService.findByName("UNKNOWN")).thenThrow(
            new RuntimeException("Unknown attestation type: UNKNOWN"));
        
        Map<String, Object> params = new HashMap<>();
        
        // When & Then
        assertThrows(RuntimeException.class, () -> 
            attestationService.generateAttestation("UNKNOWN", 1L, params));
    }
    
    @Test
    void testGenerateAttestationAddsEmployeIdToParams() throws Exception {
        // Given
        AttestationTemplate template = new AttestationTemplate();
        template.setName("TEST_TEMPLATE");
        template.setJrxml(SAMPLE_JRXML);
        
        when(attestationTemplateService.findByName("TEST_TEMPLATE")).thenReturn(template);
        
        Map<String, Object> params = new HashMap<>();
        
        // When
        assertDoesNotThrow(() -> 
            attestationService.generateAttestation("TEST_TEMPLATE", 123L, params));
        
        // Then - employeId should be added to params
        assertEquals(123L, params.get("employeId"));
    }
}
