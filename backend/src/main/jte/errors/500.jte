@template.base(
title = "The Website is Dead",
head = @`<link rel="stylesheet" type="text/css" href="css/pages/error-page.css">`,
content = @`
    <div class="error-page">
        <h2>
            500 - Internal Server Error
        </h2>
        <div class="error-page-content">
            <img src="images/500.jpg" alt="Silly looking cat">
            <div>
                <p>
                    Guess who thought it'd be a good idea to pick an enterprise-level framework to develop something
                    as silly as their own damn blog site?
                </p>
                <p>
                    Yup, you guessed it.
                </p>
                <p>
                    Dimitar did.
                </p>
                <p>
                    And because enterprise-level frameworks require enterprise-level brains, now the website is fucked.
                    Sorry about that.
                </p>
            </div>
        </div>
    </div>
`
)