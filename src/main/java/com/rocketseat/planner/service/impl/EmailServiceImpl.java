package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class EmailServiceImpl implements EmailService {

    private static final String TEMPLATE_NAME_CONFIRM_TRIP = "template-email-confirm-trip";
    private static final String TEMPLATE_NAME_CONFIRM_GUESTS = "template-email-confirm-guests";
    private static final String LOGO_EMAIL = "/static/templates/images/logo.png";
    private static final String PNG_MIME = "image/png";
    private static final String MAIL_SUBJECT = "Seja bem vindo(a)";

    @Value("${spring.url.confirm-trip}")
    private String urlConfirmTrip;

    @Value("${spring.url.confirm-guests}")
    private String urlConfirmGuests;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmailConfirGuest(Trip trip, List<String> emails) {

        String mes = trip.getEndsAt().getMonth().toString();
        String dataInicio = String.valueOf(trip.getStartsAt().getDayOfMonth());
        String dataFim = String.valueOf(trip.getEndsAt().getDayOfMonth());
        String ano = String.valueOf(trip.getEndsAt().getYear());

        HashMap<String, String> variables = new HashMap<>();
        variables.put("destino", trip.getDestination());
        variables.put("mes", mes);
        variables.put("dataInicio", dataInicio);
        variables.put("dataFim", dataFim);
        variables.put("ano", ano);
        variables.put("urlConfirmarPresenca", urlConfirmGuests);
        variables.put("logoEmail", LOGO_EMAIL);

        emails.forEach(email -> {
           CompletableFuture.runAsync(() -> {
                try {
                    this.sendEmail(variables, TEMPLATE_NAME_CONFIRM_GUESTS, LOGO_EMAIL, email);
                    System.out.println("Thread: " + Thread.currentThread().getName());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
           });
        });
    }


    @Override
    public void sendEmailConfirTrip(Trip trip) {

        String mes = trip.getEndsAt().getMonth().toString();
        String dataInicio = String.valueOf(trip.getStartsAt().getDayOfMonth());
        String dataFim = String.valueOf(trip.getEndsAt().getDayOfMonth());
        String ano = String.valueOf(trip.getEndsAt().getYear());

        HashMap<String, String> variables = new HashMap<>();
        variables.put("destino", trip.getDestination());
        variables.put("mes", mes);
        variables.put("dataInicio", dataInicio);
        variables.put("dataFim", dataFim);
        variables.put("ano", ano);
        variables.put("urlConfirmarPresenca", urlConfirmGuests);
        variables.put("logoEmail", LOGO_EMAIL);

        CompletableFuture.runAsync(() -> {
            try {
                this.sendEmail(variables, TEMPLATE_NAME_CONFIRM_TRIP, LOGO_EMAIL, trip.getOwnerEmail());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Async
    private void sendEmail(HashMap<String, String> variables, String templateName, String logoEmail, String emailTo) throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        final Context ctx = new Context(LocaleContextHolder.getLocale());

        messageHelper.setTo(emailTo);
        messageHelper.setSubject(MAIL_SUBJECT);
        messageHelper.setFrom("noreplay@gmail.com");

        variables.forEach(ctx::setVariable);

        final String htmlContent = this.templateEngine.process(templateName, ctx);
        messageHelper.setText(htmlContent, true);

        ClassPathResource clr = new ClassPathResource(logoEmail);

        messageHelper.addInline("logoEmail", clr, PNG_MIME);

        //mailSender.send(mimeMessage);
    }

}
