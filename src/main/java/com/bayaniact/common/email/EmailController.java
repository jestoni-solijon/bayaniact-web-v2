package com.bayaniact.common.email;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    /* Send plain TEXT mail */
    @PostMapping("/sendMailText")
    public String sendTextMail(
            @RequestParam(name = "recipientName") final String recipientName,
            @RequestParam(name = "recipientEmail") final String recipientEmail,
            final Locale locale)
            throws MessagingException {

        //this.emailService.sendTextMail(recipientName, recipientEmail);
        //return "redirect:/sent";
        return "redirect:/";
    }

    /* Send HTML mail (simple) */
    @PostMapping("/sendMailSimple")
    public String sendSimpleMail(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            final Locale locale)
            throws MessagingException {

        this.emailService.sendSimpleMail(recipientName, recipientEmail, locale);
        return "redirect:sent.html";

    }

    /* Send HTML mail with attachment. */
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("attachment") final MultipartFile attachment,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendMailWithAttachment(
                recipientName, recipientEmail, attachment.getOriginalFilename(),
                attachment.getBytes(), attachment.getContentType(), locale);
        return "redirect:sent.html";

    }

    /* Send HTML mail with inline image */
    @PostMapping("/sendMailWithInlineImage")
    public String sendMailWithInline(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("image") final MultipartFile image,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendMailWithInline(
                recipientName, recipientEmail, image.getName(),
                image.getBytes(), image.getContentType(), locale);
        return "redirect:sent.html";

    }

    /* Send editable HTML mail */
    @PostMapping("/sendEditableMail")
    public String sendMailWithInline(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("body") final String body,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendEditableMail(
                recipientName, recipientEmail, body, locale);
        return "redirect:sent.html";

    }

}
