package com.numan947.toolrent.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendConfirmationEmail(
            String to,
            String name,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName = emailTemplate == null ? "confirm_email" : emailTemplate.getName();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String, Object> properties = Map.of(
                "name", name,
                "confirmationUrl", confirmationUrl,
                "activationCode", activationCode
        );
        Context context = new Context();
        context.setVariables(properties);
        helper.setFrom("contact@email.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlContent = templateEngine.process(templateName, context);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }


}
