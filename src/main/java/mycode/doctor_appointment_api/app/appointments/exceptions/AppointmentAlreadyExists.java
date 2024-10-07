package mycode.doctor_appointment_api.app.appointments.exceptions;

public class AppointmentAlreadyExists extends RuntimeException{

    public AppointmentAlreadyExists(String message){super(message);}

}
