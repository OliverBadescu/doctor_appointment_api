package mycode.doctor_appointment_api.app.system.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Email service implementation using Spring Mail with Gmail SMTP.
 *
 * Configuration required in application.properties:
 * spring.mail.host=smtp.gmail.com
 * spring.mail.port=587
 * spring.mail.username=YOUR_EMAIL@gmail.com
 * spring.mail.password=YOUR_APP_PASSWORD
 * spring.mail.properties.mail.smtp.auth=true
 * spring.mail.properties.mail.smtp.starttls.enable=true
 * app.base-url=http://localhost:8080
 */
@Service
@Slf4j
public class SpringMailEmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public SpringMailEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendConfirmationEmail(String to, String patientName, String doctorName,
                                      String appointmentTime, String confirmationToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Confirm Your Doctor Appointment");

            String confirmationLink = baseUrl + "/api/v1/appointment/confirm/" + confirmationToken;
            String htmlContent = buildEmailContent(patientName, doctorName, appointmentTime, confirmationLink);

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Confirmation email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Error sending confirmation email to: {}", to, e);
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }

    private String buildEmailContent(String patientName, String doctorName,
                                     String appointmentTime, String confirmationLink) {
        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                        .content { padding: 20px; background-color: #f9f9f9; }
                        .button {
                            display: inline-block;
                            padding: 12px 24px;
                            margin: 20px 0;
                            background-color: #4CAF50;
                            color: white;
                            text-decoration: none;
                            border-radius: 4px;
                        }
                        .footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>Appointment Confirmation Required</h1>
                        </div>
                        <div class="content">
                            <p>Hello %s,</p>
                            <p>You have requested an appointment with <strong>Dr. %s</strong> on <strong>%s</strong>.</p>
                            <p>Please confirm your appointment by clicking the button below:</p>
                            <center>
                                <a href="%s" class="button">Confirm Appointment</a>
                            </center>
                            <p>Or copy and paste this link into your browser:</p>
                            <p style="word-break: break-all; color: #4CAF50;">%s</p>
                            <p><strong>Important:</strong> Your appointment will remain pending until confirmed.</p>
                        </div>
                        <div class="footer">
                            <p>If you did not request this appointment, please ignore this email.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, patientName, doctorName, appointmentTime, confirmationLink, confirmationLink);
    }
}
