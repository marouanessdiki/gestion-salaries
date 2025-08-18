package com.netcon.gestion_salaries.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.controller.mappers.AttestationCmdMapper;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.service.inteface.IAttestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttestationRestController.class)
@ActiveProfiles("test")
class AttestationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAttestationService attestationService;

    @MockBean
    private AttestationCmdMapper attestationCmdMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AttestationDto mockAttestation;
    private AttestationCmd mockAttestationCmd;

    @BeforeEach
    void setUp() {
        mockAttestation = new AttestationDto();
        mockAttestation.setId(1L);
        mockAttestation.setEmployeId(1L);
        mockAttestation.setTypeAttestation("Travail");
        mockAttestation.setDateGeneration(LocalDateTime.now());
        mockAttestation.setCheminFichier("pdfs/attestation_1.pdf");

        mockAttestationCmd = new AttestationCmd();
        mockAttestationCmd.setEmployeId(1L);
        mockAttestationCmd.setTypeAttestation("Travail");

        // Setup mapper mock
        when(attestationCmdMapper.from(any(AttestationCmd.class))).thenReturn(mockAttestation);
    }

    @Test
    void testFindAll() throws Exception {
        // Given
        List<AttestationDto> attestations = Arrays.asList(mockAttestation);
        when(attestationService.findAll()).thenReturn(attestations);

        // When & Then
        mockMvc.perform(get("/api/attestations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].employeId").value(1))
                .andExpect(jsonPath("$[0].typeAttestation").value("Travail"));

        verify(attestationService).findAll();
    }

    @Test
    void testFindByEmploye() throws Exception {
        // Given
        Long employeId = 1L;
        List<AttestationDto> attestations = Arrays.asList(mockAttestation);
        when(attestationService.findByEmploye(employeId)).thenReturn(attestations);

        // When & Then
        mockMvc.perform(get("/api/attestations/employe/{id}", employeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].employeId").value(1));

        verify(attestationService).findByEmploye(employeId);
    }

    @Test
    void testSave() throws Exception {
        // Given
        when(attestationService.generateAndSave(any(AttestationDto.class))).thenReturn(mockAttestation);

        // When & Then
        mockMvc.perform(post("/api/attestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockAttestationCmd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.employeId").value(1))
                .andExpect(jsonPath("$.typeAttestation").value("Travail"));

        verify(attestationService).generateAndSave(any(AttestationDto.class));
        verify(attestationCmdMapper).from(any(AttestationCmd.class));
    }

    @Test
    void testDelete() throws Exception {
        // Given
        Long attestationId = 1L;
        doNothing().when(attestationService).deleteById(attestationId);

        // When & Then
        mockMvc.perform(delete("/api/attestations/{id}", attestationId))
                .andExpect(status().isNoContent());

        verify(attestationService).deleteById(attestationId);
    }

    @Test
    void testFindAll_EmptyList() throws Exception {
        // Given
        when(attestationService.findAll()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/attestations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(attestationService).findAll();
    }

    @Test
    void testFindByEmploye_EmptyList() throws Exception {
        // Given
        Long employeId = 1L;
        when(attestationService.findByEmploye(employeId)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/attestations/employe/{id}", employeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(attestationService).findByEmploye(employeId);
    }
} 