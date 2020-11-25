package com.xbank.event.listener;

import com.xbank.event.OnRegenerateEmailVerificationEvent;
import com.xbank.exception.MailSendException;
import com.xbank.model.User;
import com.xbank.model.token.EmailVerificationToken;
import com.xbank.service.MailService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnRegenerateEmailVerificationListener implements ApplicationListener<OnRegenerateEmailVerificationEvent> {

    private final MailService mailService;

    /**
     * As soon as a registration event is complete, invoke the email verification
     */
    @Override
    @Async
    public void onApplicationEvent(OnRegenerateEmailVerificationEvent onRegenerateEmailVerificationEvent) {
        resendEmailVerification(onRegenerateEmailVerificationEvent);
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void resendEmailVerification(OnRegenerateEmailVerificationEvent event) {
        User user = event.getUser();
        EmailVerificationToken emailVerificationToken = event.getToken();
        String recipientAddress = user.getEmail();

        String emailConfirmationUrl =
                event.getRedirectUrl().queryParam("token", emailVerificationToken.getToken()).toUriString();
        try {
            mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("Error send mail verify!", e);
            throw new MailSendException(recipientAddress, "Email Verification");
        }
    }

}
