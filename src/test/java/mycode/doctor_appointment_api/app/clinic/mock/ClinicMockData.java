package mycode.doctor_appointment_api.app.clinic.mock;

import mycode.doctor_appointment_api.app.clinic.model.Clinic;

public class ClinicMockData {

    public static Clinic createClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("Test Clinic");
        clinic.setAddress("123 Main St");
        return clinic;
    }

    public static Clinic createDuplicateClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("Test Clinic");
        clinic.setAddress("123 Main St");
        return clinic;
    }

    public static Clinic createSecondClinic() {
        Clinic clinic = new Clinic();
        clinic.setName("Second Clinic");
        clinic.setAddress("456 Side Rd");
        return clinic;
    }
}
