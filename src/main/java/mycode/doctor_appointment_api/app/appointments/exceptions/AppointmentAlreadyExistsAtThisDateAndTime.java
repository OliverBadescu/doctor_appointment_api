package mycode.doctor_appointment_api.app.appointments.exceptions;

public class AppointmentAlreadyExistsAtThisDateAndTime extends RuntimeException{

    public AppointmentAlreadyExistsAtThisDateAndTime(String message){super(message);}

}
