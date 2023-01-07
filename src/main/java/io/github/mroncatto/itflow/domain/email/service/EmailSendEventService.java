package io.github.mroncatto.itflow.domain.email.service;

import io.github.mroncatto.itflow.domain.email.repository.IEmailSendEventRepository;
import io.github.mroncatto.itflow.domain.email.model.EmailSendEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EmailSendEventService {

    private final IEmailSendEventRepository sendEventRepository;

    public void save(EmailSendEvent event) {
        this.sendEventRepository.save(event);
    }

    public List<EmailSendEvent> getSendEventPending() {
        return this.sendEventRepository.findAllBySentIsFalse();
    }
}
