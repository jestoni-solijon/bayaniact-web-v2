package com.bayaniact.common.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Incident;
import com.bayaniact.common.entity.Request;
import com.bayaniact.common.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    /*Form Request*/
    // 1. Approved
    private static final String EMAIL_APPROVED_FORM_REQUEST = "email/request/email-approved-form-request.txt";
    // 2. Declined
    private static final String EMAIL_DECLINED_FORM_REQUEST = "email/request/email-declined-form-request.txt";
    // 3. Default Email Sender
    private static final String DEFAULT_EMAIL_SENDER = "bayaniserbisyo@gmail.com";

    /*
    * Resident Application
    * */
    private static final String EMAIL_APPROVED_RESIDENT = "email/resident/email-approved-resident.txt";

    private static final String EMAIL_DECLINED_RESIDENT = "email/resident/email-declined-resident.txt";

    /*
     * Incident
     * */
    private static final String EMAIL_INCIDENT_OFFICIAL_ASSIGNMENT = "email/incident/email-incident-official-assignment.txt";

    private static final String EMAIL_INCIDENT_OFFICIAL_ADMIN = "email/incident/email-incident-official-admin.txt";


    /*
    * Authentication
    * */
    private static final String EMAIL_VERIFICATION_TOKEN = "email/authentication/email-verification-token.txt";

    private static final String SEND_MAIL = "email/send-mail.txt";

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "email-text.txt";
    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-simple";
    private static final String EMAIL_WITHATTACHMENT_TEMPLATE_NAME = "html/email-withattachment";
    private static final String EMAIL_INLINEIMAGE_TEMPLATE_NAME = "html/email-inlineimage";
    private static final String EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES = "classpath:mail/editablehtml/email-editable.html";

    private static final String BACKGROUND_IMAGE = "mail/editablehtml/images/background.png";
    private static final String LOGO_BACKGROUND_IMAGE = "mail/editablehtml/images/logo-background.png";
    private static final String THYMELEAF_BANNER_IMAGE = "mail/editablehtml/images/thymeleaf-banner.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mail/editablehtml/images/thymeleaf-logo.png";

    private static final String PNG_MIME = "image/png";

    @Autowired private ApplicationContext applicationContext;
    @Autowired private JavaMailSender mailSender;
    @Autowired private TemplateEngine htmlTemplateEngine;
    @Autowired private TemplateEngine textTemplateEngine;
    @Autowired private TemplateEngine stringTemplateEngine;

    public void sendDeclinedFormRequest(final String recipientEmail, final String recipientFirstName, final String recipientLastName) throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientLastName + ',' + ' ' + recipientFirstName);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Form Request Declined");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(recipientEmail);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_DECLINED_FORM_REQUEST, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendApprovedFormRequest(Request request) throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", request.getResident().getLastName() + ',' + ' ' + request.getResident().getFirstName());
        ctx.setVariable("form", request.getForm().getFormName());
        ctx.setVariable("price", request.getForm().getFormPrice());
        ctx.setVariable("requestDate", request.getRequestDate());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Form Request Approved");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(request.getResident().getEmail());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_APPROVED_FORM_REQUEST, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendApprovedResident(User user) throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", user.getLastName() + ',' + ' ' + user.getFirstName());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Form Request Approved");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(user.getEmail());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_APPROVED_RESIDENT, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendDeclinedResident(User user, String reason) throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", user.getLastName() + ',' + ' ' + user.getFirstName());
        ctx.setVariable("reason", reason);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Form Request Declined");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(user.getEmail());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_DECLINED_RESIDENT, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendMail(String to, String name, String subject, String content) throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("content", content);
        ctx.setVariable("name", name);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(to);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(SEND_MAIL, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }


    public void sendIncidentOficialAssignment(BrgyOfficial brgyOfficial, Incident incident)
            throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", brgyOfficial.getFirstName());
        ctx.setVariable("incidentTitle", incident.getIncidentTitle());
        ctx.setVariable("incidentType", incident.getIncidentType());
        ctx.setVariable("incidentDesc", incident.getIncidentDesc());
        ctx.setVariable("incidentDate", incident.getIncidentDate());
        ctx.setVariable("incidentTime", incident.getIncidentTime());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Incident Assignment Notification ");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(brgyOfficial.getEmailAddress());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_INCIDENT_OFFICIAL_ASSIGNMENT, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendIncidentOficialAdmin(User user, Incident incident)
            throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", user.getFirstName());
        ctx.setVariable("reporterFirstName", incident.getFirstName());
        ctx.setVariable("reporterLastName", incident.getLastName());
        ctx.setVariable("reporterEmail", incident.getEmail());
        ctx.setVariable("reporterPhone", incident.getPhone());
        ctx.setVariable("incidentTitle", incident.getIncidentTitle());
        ctx.setVariable("incidentType", incident.getIncidentType());
        ctx.setVariable("incidentDesc", incident.getIncidentDesc());
        ctx.setVariable("incidentDate", incident.getIncidentDate());
        ctx.setVariable("incidentTime", incident.getIncidentTime());
        ctx.setVariable("incidentAddress", incident.getAddress());

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Priority Incident Notification ");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(user.getEmail());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_INCIDENT_OFFICIAL_ADMIN, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendEmailVerification(User user)
            throws MessagingException {

        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        String tokenUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/verify")
                .queryParam("token", user.getVerificationToken())
                .toUriString();

        System.out.println(tokenUrl);

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", user.getFirstName());
        ctx.setVariable("token", tokenUrl);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Email Verification");
        message.setFrom(DEFAULT_EMAIL_SENDER);
        message.setTo(user.getEmail());

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_VERIFICATION_TOKEN, ctx);

        message.setText(textContent);
        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendOTP(String to, String otp) throws MessagingException {
        // Use a fixed Locale, e.g., English
        final Locale locale = Locale.ENGLISH;

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", to);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example plain TEXT email");
        message.setFrom("test9@gmail.com");
        message.setTo(to);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);

        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send plain TEXT mail
     */
    public void sendOTP(
            String to, String otp, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", to);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example plain TEXT email");
        message.setFrom("jestoni.solijon9@gmail.com");
        message.setTo(to);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);

        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }

    /*
     * Send plain TEXT mail
     */
    public void sendTextMail(
            final String recipientName, final String recipientEmail, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example plain TEXT email");
        message.setFrom("jestoni.solijon9@gmail.com");
        message.setTo(recipientEmail);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);

        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }



    /*
     * Send HTML mail (simple)
     */
    public void sendSimpleMail(
            final String recipientName, final String recipientEmail, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example HTML email (simple)");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with attachment.
     */
    public void sendMailWithAttachment(
            final String recipientName, final String recipientEmail, final String attachmentFileName,
            final byte[] attachmentBytes, final String attachmentContentType, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example HTML email with attachment");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_WITHATTACHMENT_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the attachment
        final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
        message.addAttachment(
                attachmentFileName, attachmentSource, attachmentContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with inline image
     */
    public void sendMailWithInline(
            final String recipientName, final String recipientEmail, final String imageResourceName,
            final byte[] imageBytes, final String imageContentType, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example HTML email with inline image");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_INLINEIMAGE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with inline image
     */
    /*
     * public String getEditableMailTemplate() throws IOException { final Resource
     * templateResource =
     * this.applicationContext.getResource(EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES);
     * final InputStream inputStream = templateResource.getInputStream(); return
     * IOUtils.toString(inputStream, SpringMailConfig.EMAIL_TEMPLATE_ENCODING); }
     */


    /*
     * Send HTML mail with inline image
     */
    public void sendEditableMail(
            final String recipientName, final String recipientEmail, final String htmlContent,
            final Locale locale)
            throws MessagingException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example editable HTML email");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

        // Create the HTML body using Thymeleaf
        final String output = stringTemplateEngine.process(htmlContent, ctx);
        message.setText(output, true /* isHtml */);

        // Add the inline images, referenced from the HTML code as "cid:image-name"
        message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("logo-background", new ClassPathResource(LOGO_BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-banner", new ClassPathResource(THYMELEAF_BANNER_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

}