package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.exceptions.ClinicAlreadyExists;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.mock.ClinicMockData;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicCommandServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicCommandServiceImpl clinicCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddClinicSuccessfully() {
        CreateClinicRequest request = new CreateClinicRequest(
                "Test Clinic", "123 Main St"
        );
        // No existing clinics
        when(clinicRepository.findAll()).thenReturn(Collections.emptyList());
        Clinic saved = ClinicMockData.createClinic();
        when(clinicRepository.saveAndFlush(any(Clinic.class))).thenReturn(saved);

        ClinicResponse response = clinicCommandService.addClinic(request);

        assertEquals("Test Clinic", response.name());
        assertEquals("123 Main St", response.address());
        verify(clinicRepository).saveAndFlush(any(Clinic.class));
    }

    @Test
    void shouldThrowWhenClinicAlreadyExists() {
        CreateClinicRequest request = new CreateClinicRequest(
                "Test Clinic", "123 Main St"
        );
        // Existing duplicate clinic
        List<Clinic> existing = List.of(ClinicMockData.createDuplicateClinic());
        when(clinicRepository.findAll()).thenReturn(existing);

        assertThrows(ClinicAlreadyExists.class, () ->
                clinicCommandService.addClinic(request)
        );
        verify(clinicRepository, never()).saveAndFlush(any());
    }

    @Test
    void shouldUpdateClinicSuccessfully() {
        Clinic existing = ClinicMockData.createClinic();
        existing.setId(1);
        UpdateClinicRequest updateRequest = new UpdateClinicRequest(
                "Updated Clinic", "456 Elm St"
        );
        when(clinicRepository.findById(1)).thenReturn(Optional.of(existing));

        ClinicResponse response = clinicCommandService.updateClinic(1, updateRequest);

        assertEquals("Updated Clinic", response.name());
        assertEquals("456 Elm St", response.address());
        verify(clinicRepository).save(existing);
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentClinic() {
        when(clinicRepository.findById(99)).thenReturn(Optional.empty());
        UpdateClinicRequest updateRequest = new UpdateClinicRequest(
                "Name", "Addr"
        );

        assertThrows(NoClinicFound.class, () ->
                clinicCommandService.updateClinic(99, updateRequest)
        );
        verify(clinicRepository, never()).save(any());
    }

    @Test
    void shouldDeleteClinicSuccessfully() {
        Clinic existing = ClinicMockData.createClinic();
        existing.setId(1);
        when(clinicRepository.findById(1)).thenReturn(Optional.of(existing));

        ClinicResponse response = clinicCommandService.deleteClinic(1);

        assertEquals("Test Clinic", response.name());
        assertEquals("123 Main St", response.address());
        verify(clinicRepository).delete(existing);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentClinic() {
        when(clinicRepository.findById(42)).thenReturn(Optional.empty());

        assertThrows(NoClinicFound.class, () ->
                clinicCommandService.deleteClinic(42)
        );
        verify(clinicRepository, never()).delete(any());
    }
}
