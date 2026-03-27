@template.base(
title = "What do you mean",
head = @`<link rel="stylesheet" type="text/css" href="css/pages/error-page.css">`,
content = @`
    <div class="error-page">
        <h2>
            400 - Bad Request
        </h2>
        <div class="error-page-content">
            <img src="images/400.jpg" alt="A cat looking confused">
            <div>
                <p>
                    Whatever you did wasn't quite exactly what you were supposed do to.
                </p>
                <p>
                    The application didn't understand what you request of it and that is why we are now here.
                </p>
                <p>
                    It might be also that the application expected you to do something, but you didn't
                </p>
                <p>
                    Either way, you're gonna have to try again because no matter how many times you try doing this, it
                    will always lead you back to this page.
                </p>
            </div>
        </div>
    </div>
`
)