package mycode.doctor_appointment_api.app.email;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${mailgun.api-key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.from}")
    private String from;

    public void sendAppointmentConfirmation(String toEmail, String toName, String subject, String body) {
        try {
            log.info("Sending email to {} via Mailgun...", toEmail);

            HttpResponse<JsonNode> response = Unirest.post("https://api.eu.mailgun.net/v3/" + domain + "/messages")
                    .basicAuth("api", apiKey)
                    .queryString("from", from)
                    .queryString("to", toName + " <" + toEmail + ">")
                    .queryString("subject", subject)
                    .queryString("text", body)
                    .asJson();

            log.info("Mailgun response - Status: {}, Body: {}", response.getStatus(), response.getBody());
        } catch (UnirestException e) {
            log.error("Failed to send email to {}", toEmail, e);
        }
    }
}
