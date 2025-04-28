package mycode.doctor_appointment_api.app.clinic.service;

import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponseList;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicQueryServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicQueryServiceImpl clinicQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnClinicByIdSuccessfully() {
        Clinic clinic = ClinicMockData.createClinic();
        when(clinicRepository.findById(1)).thenReturn(Optional.of(clinic));

        ClinicResponse response = clinicQueryService.getClinicById(1);

        assertEquals(1, response.id());
        assertEquals("Test Clinic", response.name());
        assertEquals("123 Main St", response.address());
        verify(clinicRepository).findById(1);
    }

    @Test
    void shouldThrowWhenClinicByIdNotFound() {
        when(clinicRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoClinicFound.class, () -> clinicQueryService.getClinicById(99));
        verify(clinicRepository).findById(99);
    }

    @Test
    void shouldReturnAllClinicsSuccessfully() {
        Clinic first = ClinicMockData.createClinic();
        Clinic second = ClinicMockData.createSecondClinic();
        List<Clinic> clinics = List.of(first, second);
        when(clinicRepository.findAll()).thenReturn(clinics);

        ClinicResponseList responseList = clinicQueryService.getAllClinics();

        assertEquals(2, responseList.list().size());
        assertTrue(responseList.list().stream().anyMatch(r -> r.id() == 1));
        assertTrue(responseList.list().stream().anyMatch(r -> r.id() == 3));
        verify(clinicRepository).findAll();
    }

    @Test
    void shouldThrowWhenNoClinicsFound() {
        when(clinicRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoClinicFound.class, () -> clinicQueryService.getAllClinics());
        verify(clinicRepository).findAll();
    }

    @Test
    void shouldReturnTotalClinics() {
        List<Clinic> clinics = List.of(
                ClinicMockData.createClinic(),
                ClinicMockData.createSecondClinic(),
                ClinicMockData.createDuplicateClinic()
        );
        when(clinicRepository.findAll()).thenReturn(clinics);

        int total = clinicQueryService.getTotalClinics();

        assertEquals(3, total);
        verify(clinicRepository, times(1)).findAll();
    }
}
