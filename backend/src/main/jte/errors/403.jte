@template.base(
title = "This is illegal",
head = @`<link rel="stylesheet" type="text/css" href="css/pages/error-page.css">`,
content = @`
    <div class="error-page">
        <h2>
            403 - Forbidden
        </h2>
        <div class="error-page-content">
            <img src="images/403.png" alt="A cat looking at your suspiciously">
            <div>
                <p>
                    Mind explaining what are you trying to do?
                </p>
                <p>
                    Clearly you are not meant to be here, so this is why you are seeing this.
                </p>
                <p>
                    Or maybe you are meant to be here but the spaghetti code generating this page (and the entirety of
                    this site) has finally overcooked and is not doing what it's meant to be doing.
                </p>
                <p>
                    If that is the case, then please accept my apologies. If you, however, are a hax0r, then please have
                    mercy on my poor website.
                </p>
            </div>
        </div>
    </div>
`
)