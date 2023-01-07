package io.github.mroncatto.itflow.domain.email.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractEmailService;
import io.github.mroncatto.itflow.domain.email.model.EmailEventData;
import io.github.mroncatto.itflow.domain.email.model.EmailSendEvent;
import io.github.mroncatto.itflow.domain.email.model.vo.EmailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSendService extends AbstractEmailService {
    private final Configuration configuration;
    private final JavaMailSender sender;
    private final EmailSendEventService eventService;

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 60000) // Every 60 seconds
    public void sendEmail() {
        log.info(">>> Email sending service starting...");
        List<EmailSendEvent> pendingEvents = this.eventService.getSendEventPending();
        pendingEvents.stream().forEach(event -> send(event));
        log.info(">>> Email sending service done!");
    }

    private void send(EmailSendEvent event) {
        try {
            log.info(">>> sending email...");
            MimeMessage mimeMessage = sender.createMimeMessage();
            mimeMessage = buildMimeMessage(mimeMessage, event.getSubject(), buildTemplate(event.getTemplate(), event.getEventDataList()), event.getAddress());
            sender.send(mimeMessage);
            event.setSent(true);
            event.setSendDate(new Date());
            this.eventService.save(event);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
        }

    }

    private String buildTemplate(EmailTemplate template, List<EmailEventData> values) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        values.forEach(v -> {
            model.put(v.getVariable(), v.getValue());
        });
        configuration.getTemplate(template.toString() + ".ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
