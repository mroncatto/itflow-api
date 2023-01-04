package io.github.mroncatto.itflow.domain.abstracts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URI;

import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_NAME;

@Slf4j
public abstract class AbstractEmailService {

    protected String getDomain() {
        String domain = "localhost";
        try {
            URI uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
            String host = uri.getHost();
            domain = host.startsWith("www.") ? host.substring(4) : host;
        } catch (Exception e) {
            log.error("Error while getting domain url: {}", e.getMessage());
        }
        return domain;
    }

    protected MimeMessage buildMimeMessage(MimeMessage mimeMessage, String subject, String recipient, String text){
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(APP_NAME.concat(" - ").concat(subject));
            helper.setFrom("noreply@".concat(getDomain()));
            helper.setTo(recipient);
            helper.setText(text);
        } catch (MessagingException e){
            log.error("Error while building Mime Message: {}", e.getMessage());
        }
        return mimeMessage;
    }
}
