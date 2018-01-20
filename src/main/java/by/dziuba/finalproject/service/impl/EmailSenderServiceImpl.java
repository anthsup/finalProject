package by.dziuba.finalproject.service.impl;

import by.dziuba.finalproject.service.EmailSenderService;
import com.sendgrid.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EmailSenderServiceImpl implements EmailSenderService {
    private static final Logger logger = LogManager.getLogger(EmailSenderService.class);
    private static final String APP_NAME = MailProperty.getProperty(MailProperty.APP_NAME);
    private static final String TOP_LEVEL_DOMAIN_NAME = MailProperty.getProperty(MailProperty.TOP_LEVEL_DOMAIN_NAME);
    private static final String SEND_GRID_TOKEN = MailProperty.getProperty(MailProperty.SEND_GRID_TOKEN);

    @Override
    public boolean sendEmail(String message, String subject, String emailAddress) {
        boolean send = false;
        Email from = new Email(APP_NAME + "@" + APP_NAME + "." + TOP_LEVEL_DOMAIN_NAME);
        Email to = new Email(emailAddress);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SEND_GRID_TOKEN);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            sg.api(request);
            send = true;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return send;
    }
}
