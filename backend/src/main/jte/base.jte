@import gg.jte.Content
@param String title
@param Content content
@param Content head

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Dimitar on the Internet</title>
    <base href="/">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/partials/header.css">
    <link rel="stylesheet" href="css/partials/footer.css">
    <link rel="stylesheet" href="css/fonts.css">
    ${head}
</head>
<body>
<header>
    <nav>
        <a href="/">Index</a>
        <a href="/archive">Archive</a>
        <a href="/now">Now</a>
        <a href="/about">About</a>
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
