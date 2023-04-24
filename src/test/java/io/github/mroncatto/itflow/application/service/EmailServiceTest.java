package io.github.mroncatto.itflow.application.service;

import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.domain.email.entity.EmailSendEvent;
import io.github.mroncatto.itflow.domain.email.service.EmailSendEventService;
import io.github.mroncatto.itflow.domain.email.service.EmailService;
import io.github.mroncatto.itflow.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ItflowApiApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = "test")
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailSendEventService eventService;

    @Test
    void shouldCreateEmailEvent() {
        emailService.welcome(User.builder()
                .email("springboot@spring.com")
                .fullName("Spring boot")
                .build(), "123456");

        List<EmailSendEvent> events = eventService.getSendEventPending();
        Assertions.assertFalse(events.isEmpty());
    }
}