package io.github.mroncatto.itflow.domain.email.service;

import io.github.mroncatto.itflow.ItflowApiApplication;
import io.github.mroncatto.itflow.domain.email.model.EmailSendEvent;
import io.github.mroncatto.itflow.domain.user.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        assertFalse(events.isEmpty());
    }
}