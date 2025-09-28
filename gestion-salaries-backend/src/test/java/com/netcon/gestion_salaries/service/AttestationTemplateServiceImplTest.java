package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.entity.AttestationTemplate;
import com.netcon.gestion_salaries.records.AttestationTypeRequest;
import com.netcon.gestion_salaries.records.AttestationTypeResponse;
import com.netcon.gestion_salaries.repository.AttestationTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttestationTemplateServiceImplTest {
    
    @Mock
    private AttestationTemplateRepository repository;
    
    @InjectMocks
    private AttestationTemplateServiceImpl service;
    
    private AttestationTemplate testTemplate;
    private AttestationTypeRequest testRequest;
    
    @BeforeEach
    void setUp() {
        testTemplate = new AttestationTemplate();
        testTemplate.setId(1L);
        testTemplate.setName("SALAIRE");
        testTemplate.setJrxml("<?xml version=\"1.0\"?><jasperReport></jasperReport>");
        testTemplate.setUpdatedAt(LocalDateTime.now());
        
        testRequest = new AttestationTypeRequest("SALAIRE", "<?xml version=\"1.0\"?><jasperReport></jasperReport>");
    }
    
    @Test
    void testGetAllTypes() {
        when(repository.findAll()).thenReturn(List.of(testTemplate));
        
        List<AttestationTypeResponse> result = service.getAllTypes();
        
        assertEquals(1, result.size());
        AttestationTypeResponse response = result.get(0);
        assertEquals(1L, response.id());
        assertEquals("ATTESTATION", response.type());
        assertEquals("SALAIRE", response.value());
        assertEquals("Attestation Salaire", response.label());
    }
    
    @Test
    void testCreateOrUpdateNewTemplate() {
        when(repository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(AttestationTemplate.class))).thenReturn(testTemplate);
        
        AttestationTypeResponse result = service.createOrUpdate(testRequest);
        
        assertNotNull(result);
        assertEquals("SALAIRE", result.value());
        verify(repository).save(any(AttestationTemplate.class));
    }
    
    @Test
    void testCreateOrUpdateExistingTemplate() {
        when(repository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(testTemplate));
        when(repository.save(any(AttestationTemplate.class))).thenReturn(testTemplate);
        
        AttestationTypeResponse result = service.createOrUpdate(testRequest);
        
        assertNotNull(result);
        assertEquals("SALAIRE", result.value());
        verify(repository).save(testTemplate);
    }
    
    @Test
    void testFindByName() {
        when(repository.findByNameIgnoreCase("SALAIRE")).thenReturn(Optional.of(testTemplate));
        
        AttestationTemplate result = service.findByName("SALAIRE");
        
        assertNotNull(result);
        assertEquals("SALAIRE", result.getName());
    }
    
    @Test
    void testFindByNameNotFound() {
        when(repository.findByNameIgnoreCase("UNKNOWN")).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> service.findByName("UNKNOWN"));
    }
    
    @Test
    void testValidateTemplateName() {
        // Test valid names
        assertDoesNotThrow(() -> new AttestationTypeRequest("SALAIRE", "<?xml version=\"1.0\"?><jasperReport></jasperReport>"));
        assertDoesNotThrow(() -> new AttestationTypeRequest("TRAVAIL_TEMP", "<?xml version=\"1.0\"?><jasperReport></jasperReport>"));
        
        // Test invalid JRXML
        assertThrows(IllegalArgumentException.class, () -> 
            new AttestationTypeRequest("SALAIRE", "invalid jrxml content"));
    }
}
