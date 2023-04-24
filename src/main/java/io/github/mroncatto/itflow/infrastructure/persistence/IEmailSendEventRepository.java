package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.application.model.IAbstractRepository;
import io.github.mroncatto.itflow.domain.email.entity.EmailSendEvent;

import java.util.List;

public interface IEmailSendEventRepository extends IAbstractRepository<EmailSendEvent, Long> {

    List<EmailSendEvent> findAllBySentIsFalse();
}
