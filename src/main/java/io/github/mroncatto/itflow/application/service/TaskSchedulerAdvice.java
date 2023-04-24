package io.github.mroncatto.itflow.application.service;

import io.github.mroncatto.itflow.domain.email.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TaskSchedulerAdvice {
    private final EmailSendService emailSendService;

    @Scheduled(fixedDelay = 60000, initialDelay = 60000) // Every 60 seconds
    @Transactional
    public void sendEmailSchedule() {
        this.emailSendService.sendEmail();
    }
}
