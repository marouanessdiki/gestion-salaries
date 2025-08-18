package com.netcon.gestion_salaries.service;

import com.netcon.gestion_salaries.dao.inteface.IAttestationDao;
import com.netcon.gestion_salaries.dao.inteface.IEmployeDao;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttestationServiceImplTest {

    @Mock
    private IAttestationDao attestationDao;

    @Mock
    private IEmployeDao employeDao;

    @InjectMocks
    private AttestationServiceImpl attestationService;

    private EmployeDto mockEmploye;
    private AttestationDto mockAttestation;

    @BeforeEach
    void setUp() {
        mockEmploye = new EmployeDto();
        mockEmploye.setId(1L);
        mockEmploye.setNom("Dupont");
        mockEmploye.setPrenom("Jean");
        mockEmploye.setCin("AB123456");
        mockEmploye.setPoste("Développeur");
        mockEmploye.setService("Informatique");
        mockEmploye.setDateEmbauche(LocalDate.of(2023, 1, 15));

        mockAttestation = new AttestationDto();
        mockAttestation.setId(1L);
        mockAttestation.setEmployeId(1L);
        mockAttestation.setTypeAttestation("Travail");
        mockAttestation.setDateGeneration(LocalDateTime.now());
        mockAttestation.setCheminFichier("pdfs/attestation_1.pdf");
    }

    @Test
    void testFindByEmploye() {
        // Given
        Long employeId = 1L;
        List<AttestationDto> expectedAttestations = Arrays.asList(mockAttestation);
        when(attestationDao.findByEmployeId(employeId)).thenReturn(expectedAttestations);

        // When
        List<AttestationDto> result = attestationService.findByEmploye(employeId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockAttestation, result.get(0));
        verify(attestationDao).findByEmployeId(employeId);
    }

    @Test
    void testSave() {
        // Given
        AttestationDto attestationToSave = new AttestationDto();
        attestationToSave.setEmployeId(1L);
        attestationToSave.setTypeAttestation("Travail");
        
        when(attestationDao.save(any(AttestationDto.class))).thenReturn(mockAttestation);

        // When
        AttestationDto result = attestationService.save(attestationToSave);

        // Then
        assertNotNull(result);
        assertEquals(mockAttestation.getId(), result.getId());
        assertNotNull(result.getDateGeneration());
        verify(attestationDao).save(any(AttestationDto.class));
    }

    @Test
    void testFindAll() {
        // Given
        List<AttestationDto> expectedAttestations = Arrays.asList(mockAttestation);
        when(attestationDao.findAll()).thenReturn(expectedAttestations);

        // When
        List<AttestationDto> result = attestationService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockAttestation, result.get(0));
        verify(attestationDao).findAll();
    }

    @Test
    void testFindById() {
        // Given
        Long attestationId = 1L;
        when(attestationDao.findById(attestationId)).thenReturn(mockAttestation);

        // When
        AttestationDto result = attestationService.findById(attestationId);

        // Then
        assertNotNull(result);
        assertEquals(mockAttestation.getId(), result.getId());
        verify(attestationDao).findById(attestationId);
    }

    @Test
    void testDeleteById() {
        // Given
        Long attestationId = 1L;
        doNothing().when(attestationDao).deleteById(attestationId);

        // When
        attestationService.deleteById(attestationId);

        // Then
        verify(attestationDao).deleteById(attestationId);
    }

    @Test
    void testGenerateAndSave_Success() throws Exception {
        // Given
        AttestationDto attestationToGenerate = new AttestationDto();
        attestationToGenerate.setEmployeId(1L);
        attestationToGenerate.setTypeAttestation("Travail");

        when(employeDao.findById(1L)).thenReturn(Optional.of(mockEmploye));
        when(attestationDao.save(any(AttestationDto.class))).thenReturn(mockAttestation);
        doNothing().when(attestationDao).updateCheminFichier(anyLong(), anyString());

        // When
        AttestationDto result = attestationService.generateAndSave(attestationToGenerate);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCheminFichier());
        verify(employeDao).findById(1L);
        verify(attestationDao, times(1)).save(any(AttestationDto.class));
        verify(attestationDao).updateCheminFichier(anyLong(), anyString());
    }

    @Test
    void testGenerateAndSave_EmployeeNotFound() {
        // Given
        AttestationDto attestationToGenerate = new AttestationDto();
        attestationToGenerate.setEmployeId(999L);
        attestationToGenerate.setTypeAttestation("Travail");

        when(employeDao.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            attestationService.generateAndSave(attestationToGenerate);
        });

        verify(employeDao).findById(999L);
        verify(attestationDao, never()).save(any(AttestationDto.class));
    }

    @Test
    void testGenerateAndSave_WithSalaireType() throws Exception {
        // Given
        AttestationDto attestationToGenerate = new AttestationDto();
        attestationToGenerate.setEmployeId(1L);
        attestationToGenerate.setTypeAttestation("Salaire");

        when(employeDao.findById(1L)).thenReturn(Optional.of(mockEmploye));
        when(attestationDao.save(any(AttestationDto.class))).thenReturn(mockAttestation);
        doNothing().when(attestationDao).updateCheminFichier(anyLong(), anyString());

        // When
        AttestationDto result = attestationService.generateAndSave(attestationToGenerate);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCheminFichier());
        verify(employeDao).findById(1L);
        verify(attestationDao, times(1)).save(any(AttestationDto.class));
        verify(attestationDao).updateCheminFichier(anyLong(), anyString());
    }

    @Test
    void testGenerateAndSave_WithNullType() throws Exception {
        // Given
        AttestationDto attestationToGenerate = new AttestationDto();
        attestationToGenerate.setEmployeId(1L);
        attestationToGenerate.setTypeAttestation(null);

        when(employeDao.findById(1L)).thenReturn(Optional.of(mockEmploye));
        when(attestationDao.save(any(AttestationDto.class))).thenReturn(mockAttestation);
        doNothing().when(attestationDao).updateCheminFichier(anyLong(), anyString());

        // When
        AttestationDto result = attestationService.generateAndSave(attestationToGenerate);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCheminFichier());
        verify(employeDao).findById(1L);
        verify(attestationDao, times(1)).save(any(AttestationDto.class));
        verify(attestationDao).updateCheminFichier(anyLong(), anyString());
    }
} 