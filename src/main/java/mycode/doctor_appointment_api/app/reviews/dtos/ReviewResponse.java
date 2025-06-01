package mycode.doctor_appointment_api.app.reviews.dtos;

public record ReviewResponse(int id,String description, String title, int rating, int doctorId, long userId) {
}
