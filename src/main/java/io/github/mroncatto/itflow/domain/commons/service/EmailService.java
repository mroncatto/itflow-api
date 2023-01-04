package io.github.mroncatto.itflow.domain.commons.service;

import freemarker.template.Configuration;
import io.github.mroncatto.itflow.domain.abstracts.AbstractEmailService;
import io.github.mroncatto.itflow.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService extends AbstractEmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public void welcome(User user, String password) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage = buildMimeMessage(mimeMessage,"Welcome", user.getEmail(), password);
            javaMailSender.send(mimeMessage);
    }

    public void resetPassword(User user, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage = buildMimeMessage(mimeMessage,"Password reset", user.getEmail(), password);
        javaMailSender.send(mimeMessage);
    }

}
