package com.example.demo.mail;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.*;

import java.io.IOException;

@Service
public class EmailService {
    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    public void sendRegistrationEmail(String toEmail, String userName) throws IOException {
        Email from = new Email("efehancekic436@gmail.com");
        String subject = "Willkommen in unserem Online-Shop!";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Hallo " + userName + ",\n\n" +
                "Vielen Dank für deine Registrierung bei unserer Anwendung. " +
                "Wir freuen uns, dich an Bord zu haben!\n\n" +
                "Viele Grüße,\n" +
                "Dein Team \n" +
                "Outfitly");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("E-Mail gesendet mit Statuscode: " + response.getStatusCode());
        } catch (IOException ex) {
            throw ex;
        }
    }
}