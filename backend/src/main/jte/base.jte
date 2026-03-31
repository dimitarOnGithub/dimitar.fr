@import gg.jte.Content
@param String title
@param Content content
@param Content head

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <base href="/">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/static/css/styles.css">
    <link rel="stylesheet" href="/static/css/partials/header.css">
    <link rel="stylesheet" href="/static/css/partials/footer.css">
    <link rel="stylesheet" href="/static/css/fonts.css">
    <link rel="icon" type="image/x-icon" href="/static/icons/favicon.ico">
    ${head}
</head>
<body>
<header>
    <nav>
        <a href="/">home</a>
        <span>-</span>
        <a href="/archive">archive</a>
        <span>-</span>
        <a href="/now">now</a>
        <span>-</span>
        <a href="/about">about</a>
    </nav>
</header>
${content}
<div class="footer">
    <a href="/guestbook">guestbook</a>
    <span> - </span>
    <a href="/contact">contact</a>
</div>
</body>
</html>
