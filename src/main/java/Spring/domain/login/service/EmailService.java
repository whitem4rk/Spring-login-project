package Spring.domain.login.service;

import Spring.domain.login.exception.EmailSendFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String ENCODING_UTF8 = "UTF-8";
    private final JavaMailSender javaMailSender;

    @Async
    public void sendHtmlTextEmail(String subject, String content, String email) {
        final SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendFailException();
        }
    }

}
