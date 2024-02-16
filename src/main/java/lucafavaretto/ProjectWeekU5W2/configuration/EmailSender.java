package lucafavaretto.ProjectWeekU5W2.configuration;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class EmailSender {
    private final String apiKey;


    public EmailSender(@Value("${sendgrid.api}") String apiKey) {
        this.apiKey = apiKey;
    }

    ;

    public void sendRegistrationEmail(String recipient) throws IOException {
        Email from = new Email("favabest@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Congratulation you have a new device to work!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
}
