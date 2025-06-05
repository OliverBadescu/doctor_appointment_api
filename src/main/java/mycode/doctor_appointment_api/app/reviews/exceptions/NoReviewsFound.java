package mycode.doctor_appointment_api.app.reviews.exceptions;

public class NoReviewsFound extends RuntimeException {
    public NoReviewsFound(String message) {
        super(message);
    }
}
