package fr.dimitar.web.contact;

import fr.dimitar.web.contact.forms.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender mailSender;

    @Value("${mail.sender.address}")
    private String senderAddress;

    @Value("${mail.recipient.address}")
    private String recipientAddress;

    @Autowired
    public ContactService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void processContactForm(ContactForm form) {
        var mail = new SimpleMailMessage();
        mail.setTo(this.recipientAddress);
        mail.setFrom(this.senderAddress);
        mail.setSubject("New contact from: " + form.getEmail());
        mail.setReplyTo(form.getEmail());
        mail.setText(form.getMessage());
        this.mailSender.send(mail);
    }

}
