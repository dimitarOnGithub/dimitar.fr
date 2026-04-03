
@template.base(
title = "Dimitar's Now",
head = @`<link rel="stylesheet" href="/static/css/pages/now.css">`,
content = @`
    <div class="now-body">
        <p class="description">
            This page is inspired by Derek Sivers' "now", see
            <a class="highlight" target="_blank" href="https://nownownow.com/about">nownownow</a>
            for more information on how to create your own!
        </p>
        <h2>Log Entry #001</h2>
        <div class="last-updated">
            April, 2026
        </div>
        <p>
            Currently building this site from the ground up. I'm mostly done with the Ansible playbooks for the VPS setup,
            at least the general structure, still have to refine them and get most of the hardcoded stuff out.
        </p>
        <p>
            On the CI/CD front, I have two separate instances of the site running - this one and a stage instance which
            I'll use to test and verify changes; still trying to come up with a particular design that I like. I think
            that goes for everything in general, I keep experimenting with how to run the site, so if you somehow end up
            being here regularly, know that it will likely go through a few iterations.
        </p>
        <p>
            The job hunt has been (a little bit) soul crushing at times, especially after finding myself on the market
            for the first time in nearly 15 years. I made good progress on quite a few applications only to end up being
            ghosted in the end. But hey, complaining about it won't change a thing, so I keep going.
        </p>
        <p>
            Outside of that, trying to keep mountain biking as often as possible, weather is finally improving and it's
            nice to feel the warmth of the sun again
        </p>
        <p>
            I've been trying to be a bit more consistent with reading again, you can check out my
            <a class="highlight" target="_blank" href="https://www.goodreads.com/user/show/136298234-dimitar">
                Goodreads
            </a>
            account as I tend to keep that up to date as I read various stuff.
        </p>
    </div>
`
)