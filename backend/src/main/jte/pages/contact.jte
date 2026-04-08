@import fr.dimitar.web.contact.forms.ContactForm
@import org.springframework.security.web.csrf.CsrfToken

@param CsrfToken csrfToken
@param ContactForm contactForm


@template.base(
title = "Contact Dimitar",
head = @`<link rel="stylesheet" type="text/css" href="/static/css/pages/contact.css">`,
content = @`
    <div class="contact">
        <h2>Get In Touch</h2>
        @if(contactForm == null)
            Thank you for sending your message! I will do my best to get back to you as soon as possible!
        @else
            <form class="contact-form" action="/contact" method="post">
                @if(csrfToken != null)
                    <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}">
                @endif
                <label class="message-label">Message
                    <textarea autocomplete="off" required name="message" placeholder="Your message"></textarea>
                </label>
                <label class="name-label">Name
                    <input type="text" autocomplete="off" name="name" placeholder="Your name">
                </label>
                <label class="email-label">Email
                    <input type="email" autocomplete="on" name="email" placeholder="Your email address">
                </label>
                <input type="submit" value="Here Goes Nothing">
            </form>
        @endif
    </div>
`
)