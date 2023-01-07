package io.github.mroncatto.itflow.domain.email.repository;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractRepository;
import io.github.mroncatto.itflow.domain.email.model.EmailSendEvent;

import java.util.List;

public interface IEmailSendEventRepository extends IAbstractRepository<EmailSendEvent, Long> {

    List<EmailSendEvent> findAllBySentIsFalse();
}
