package io.github.mroncatto.itflow.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;

    public String getMessage(String message) {
        java.util.Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    public String getMessage(String message, String...args) {
        java.util.Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, args, locale);
    }

    public String getMessageNotFound(String field) {
        java.util.Locale locale = LocaleContextHolder.getLocale();
        String notFoundMessage = messageSource.getMessage("exception.not_found", null, locale);
        String fieldMessage = messageSource.getMessage("field.".concat(field), null, locale);
        return fieldMessage.concat(": " ).concat(notFoundMessage);
    }

}
