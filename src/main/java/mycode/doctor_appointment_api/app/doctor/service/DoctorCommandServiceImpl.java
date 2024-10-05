package mycode.doctor_appointment_api.app.doctor.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.CreateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.UpdateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.exceptions.DoctorAlreadyExists;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mapper.DoctorMapper;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorCommandServiceImpl implements DoctorCommandService{

    DoctorRepository doctorRepository;
    ClinicRepository clinicRepository;

    @Override
    public DoctorResponse addDoctor(CreateDoctorRequest rq) {
        Clinic clinic = clinicRepository.findByName(rq.clinic())
                .orElseThrow(() -> new NoClinicFound("No clinic with this name found"));


        Doctor doctor = Doctor.builder()
                .clinic(clinic)
                .email(rq.email())
                .fullName(rq.fullName())
                .password(rq.password())
                .phone(rq.phone())
                .specialization(rq.specialization())
                .build();

        List<Doctor> list = doctorRepository.findAll();

        list.forEach(doctor1 -> {
            if(doctor1.getFullName().equals(doctor.getFullName()) && doctor1.getEmail().equals(doctor.getEmail())){
                throw new DoctorAlreadyExists("Doctor with this name and email already exists");
            }
        });

        doctorRepository.saveAndFlush(doctor);

        return DoctorMapper.doctorToResponseDto(doctor);

    }

    @Override
    public DoctorResponse updateDoctor(UpdateDoctorRequest updateDoctorRequest, int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        doctor.setEmail(updateDoctorRequest.email());
        doctor.setFullName(updateDoctorRequest.fullName());
        doctor.setPassword(updateDoctorRequest.password());
        doctor.setPhone(updateDoctorRequest.phone());
        doctor.setSpecialization(updateDoctorRequest.specialization());


        doctorRepository.save(doctor);

        return DoctorMapper.doctorToResponseDto(doctor);
    }

    @Override
    public DoctorResponse deleteDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        DoctorResponse doctorResponse = DoctorMapper.doctorToResponseDto(doctor);

        doctorRepository.delete(doctor);

        return doctorResponse;
    }
}
