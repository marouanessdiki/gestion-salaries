package com.netcon.gestion_salaries.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttestationServiceImplTest {

    @Test
    void testTemplateExists() {
        // Test that the Jasper template file exists
        ClassPathResource resource = new ClassPathResource("reports/attestation_template.jrxml");
        assertTrue(resource.exists(), "Jasper template should exist");
    }

    @Test
    void testImagesExist() {
        // Test that the required images exist
        ClassPathResource logoResource = new ClassPathResource("images/logo.png");
        ClassPathResource cachetResource = new ClassPathResource("images/cachet.png");
        
        assertTrue(logoResource.exists(), "Logo image should exist");
        assertTrue(cachetResource.exists(), "Cachet image should exist");
    }
} 