@import fr.dimitar.web.guestbook.forms.GuestbookForm
@import org.springframework.security.web.csrf.CsrfToken
@import java.util.List

@param fr.dimitar.web.guestbook.forms.GuestbookForm guestbookForm = null
@param CsrfToken csrfToken
@param List<GuestbookForm> listOfEntries

@template.base(
title = "Dimitar's Guestbook",
head = @`<link rel="stylesheet" href="css/pages/guestbook.css">`,
content = @`
    <div class="guestbook">
        <div class="about">
            <p>
                Back when I was growing up (albeit late by the standards of the true old timers), stuff like guestbooks, shoutout boxes and forums, was cool.
            </p>
            <p>
                Although I truly doubt we'd ever be able to recapture those times, I'm glad I had the pleasure of seeing them and living through them; the times
                when the internet wasn't this overreaching entity but rather a place to be explored; a place where we didn't hide behind personas but rather used
                them as an avatar of who we were behind the keyboard; a place where meeting someone from the other side of the world wasn't a daily occurrence and
                was something we eagerly cherished.
            </p>
            <p>
                It was either that or I'm truly having an old man yells at the clouds moment. Either way, you can leave a message, if you'd like, but please note that
                there's a one message per person (or a very good bot) limit. :)
            </p>
        </div>
        @if(guestbookForm != null)
            <div class="guestbook-form">
                <form action="/guestbook" method="post">
                    @if(csrfToken != null)
                        <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}">
                    @endif
                    <label>Your message is, unsurprisingly, a required field
                        <textarea required class="message" name="content" placeholder="Plain text only, please, input sanitization is not my forte"></textarea>
                    </label>
                    <label>Name can be left empty
                        <input type="text" class="username" name="username" placeholder="This will appear next to your entry">
                    </label>
                    <label class="alias">Alias is also a required field
                        <input type="text" name="alias" placeholder="Do you have an alias you go by?">
                    </label>
                    <label>Your site can also be empty but if filled out, your entry will link to it
                        <input type="url" name="userWebsite" placeholder="Link to your place on the net">
                    </label>
                    <div class="button-wrap">
                        <input type="submit" value="Here Goes Nothing">
                    </div>
                </form>
            </div>
        @endif
        <div class="entry-list">
            @if(listOfEntries.isEmpty())
                No one has posted anything :-(
            @else
                @for(fr.dimitar.web.guestbook.forms.GuestbookForm entry: listOfEntries)
                    <div class="entry">
                        <div class="entry-content content">
                            <p>${entry.getContent()}</p>
                        </div>
                        <div class="entry-details">
                            @if(entry.getUsername() != null)
                                @if(entry.getUserWebsite() != null)
                                    <a class="highlight" href="${entry.getUserWebsite()}" target="_blank">
                                        ${entry.getUsername()}
                                    </a>
                                @else
                                    ${entry.getUsername()}
                                @endif
                            @else
                                Anonymous
                            @endif
                        </div>
                    </div>
                @endfor
            @endif
        </div>
    </div>
`
)