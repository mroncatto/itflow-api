package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.domain.email.model.EmailEventData;
import io.github.mroncatto.itflow.domain.email.model.EmailSendRecipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_NAME;

@Slf4j
public abstract class AbstractEmailService {

//    protected String getDomain() {
//        String domain = "localhost";
//        try {
//            URI uri = new URI(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
//            String host = uri.getHost();
//            domain = host.startsWith("www.") ? host.substring(4) : host;
//        } catch (Exception e) {
//            log.error("Error while getting domain url: {}", e.getMessage());
//        }
//        return domain;
//    }

    private String getDomain() {
        return "itflow.com"; //TODO: Criar metodo para registrar dominio
    }

    protected MimeMessage buildMimeMessage(MimeMessage mimeMessage, String subject, String body, String... recipients){
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(APP_NAME.concat(" - ").concat(subject));
            helper.setFrom("noreply@".concat(getDomain()));
            helper.setTo(recipients);
            helper.setText(body, true);
        } catch (MessagingException e){
            log.error("Error while building Mime Message: {}", e.getMessage());
        }
        return mimeMessage;
    }

    protected List<EmailSendRecipient> getRecipients(String... email) {
        List<EmailSendRecipient> recipients = new ArrayList<>();
        Arrays.stream(email).forEach(e -> {
            recipients.add(
                    EmailSendRecipient.builder()
                            .recipient(e)
                            .build());
        });
        return recipients;
    }

    protected List<EmailEventData> getVariables(List<String> variables, String... values) throws Exception {
        if (variables.size() != values.length) throw new Exception("ERROR COMPOSING EMAIL: The parameters and variables do not match");
        List<EmailEventData> eventDataList = new ArrayList<>();
        variables.stream().forEach(v -> {
            eventDataList.add(
                    EmailEventData.builder()
                            .variable(v)
                            .value(values[variables.indexOf(v)])
                            .build());
        });
        return eventDataList;
    }
}
