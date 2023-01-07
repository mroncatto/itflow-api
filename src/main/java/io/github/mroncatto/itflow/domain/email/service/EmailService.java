package io.github.mroncatto.itflow.domain.email.service;

import io.github.mroncatto.itflow.domain.abstracts.AbstractEmailService;
import io.github.mroncatto.itflow.domain.email.model.EmailSendEvent;
import io.github.mroncatto.itflow.domain.email.model.vo.EmailTemplate;
import io.github.mroncatto.itflow.domain.email.model.vo.ResetPasswordVariable;
import io.github.mroncatto.itflow.domain.email.model.vo.WelcomeVariable;
import io.github.mroncatto.itflow.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService extends AbstractEmailService {
    private final EmailSendEventService eventService;

    public void welcome(User user, String password){
        try {
        EmailSendEvent event = EmailSendEvent.builder()
                .eventDate(new Date())
                .template(EmailTemplate.WELCOME)
                .subject("Welcome")
                .recipients(getRecipients(user.getEmail()))
                .eventDataList(getVariables(WelcomeVariable.getValues(), password))
                .build();

        this.eventService.save(event);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void resetPassword(User user, String password) {
        try {
            EmailSendEvent event = EmailSendEvent.builder()
                    .eventDate(new Date())
                    .template(EmailTemplate.RESET_PASSWORD)
                    .subject("Password Reset")
                    .recipients(getRecipients(user.getEmail()))
                    .eventDataList(getVariables(ResetPasswordVariable.getValues(), password))
                    .build();

            this.eventService.save(event);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
