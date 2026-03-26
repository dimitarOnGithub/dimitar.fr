

@template.base(
title = "Not Found?",
head = @`<link rel="stylesheet" type="text/css" href="css/pages/404.css">`,
content = @`
    <div class="error-page">
        <h2>
            404 - Not Found
        </h2>
        <div class="error-page-content">
            <img src="images/404.png" alt="A photo of a cat looking confused">
            <div>
                <p>
                    Something here isn't quite right, it seems. You are seeing this error page because you tried to visit a page
                    that no longer exists.
                </p>
                <p>
                    or never existed
                </p>
                <p>
                    or never will exist
                </p>
                <p>
                    but all that is irrelevant because it doesn't exist right at this moment.
                </p>
            </div>
        </div>
    </div>
`
)