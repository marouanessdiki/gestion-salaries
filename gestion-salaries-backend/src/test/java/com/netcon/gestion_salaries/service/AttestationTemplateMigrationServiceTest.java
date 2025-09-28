package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.entity.AttestationTemplate;
import com.netcon.gestion_salaries.repository.AttestationTemplateRepository;
import com.netcon.gestion_salaries.util.TemplateIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttestationTemplateMigrationServiceTest {
    
    @Mock
    private AttestationTemplateRepository repository;
    
    @Mock
    private TemplateIO templateIO;
    
    @InjectMocks
    private AttestationTemplateMigrationService migrationService;
    
    private static final String SAMPLE_JRXML = "<?xml version=\"1.0\"?><jasperReport></jasperReport>";
    
    @BeforeEach
    void setUp() {
        // Mock repository responses
        when(repository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(repository.count()).thenReturn(0L);
        when(repository.save(any(AttestationTemplate.class))).thenAnswer(invocation -> {
            AttestationTemplate template = invocation.getArgument(0);
            template.setId(1L);
            return template;
        });
        
        // Mock TemplateIO responses
        when(templateIO.readFromClasspath(anyString())).thenReturn(SAMPLE_JRXML);
    }
    
    @Test
    void testMigrateTemplates() {
        // When
        migrationService.migrateTemplates();
        
        // Then
        verify(repository, atLeastOnce()).save(any(AttestationTemplate.class));
        verify(templateIO, atLeastOnce()).writeToDisk(anyString(), anyString());
    }
    
    @Test
    void testIsTemplateExists() {
        // Given
        when(repository.existsByNameIgnoreCase("TRAVAIL")).thenReturn(true);
        
        // When
        boolean exists = migrationService.isTemplateExists("TRAVAIL");
        
        // Then
        assertTrue(exists);
        verify(repository).existsByNameIgnoreCase("TRAVAIL");
    }
    
    @Test
    void testGetTemplateCount() {
        // Given
        when(repository.count()).thenReturn(5L);
        
        // When
        long count = migrationService.getTemplateCount();
        
        // Then
        assertEquals(5L, count);
        verify(repository).count();
    }
    
    @Test
    void testMigrateTemplatesSkipsExisting() {
        // Given
        when(repository.existsByNameIgnoreCase("SALAIRE")).thenReturn(true);
        when(repository.existsByNameIgnoreCase("TRAVAIL")).thenReturn(false);
        
        // When
        migrationService.migrateTemplates();
        
        // Then
        verify(repository, never()).save(any(AttestationTemplate.class));
        verify(templateIO, never()).writeToDisk(anyString(), anyString());
    }
}
