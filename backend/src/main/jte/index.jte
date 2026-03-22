@import java.util.List
@import fr.dimitar.web.posts.dto.PostModel

@param List<PostModel> posts

@template.base(
title = "Dimitar On The Internet",
head = @`<link rel="stylesheet" href="css/pages/index.css">`,
content = @`
    <div class="main-content">
        <div class="left-column">
            <div class="text-area">
                <p>
                    Hi.
                </p>
                <p>
                    Good to have you here.
                </p>
                <p>
                    I've never been good at introducing, presenting and/or selling myself, so I'll save us the embarrassment.
                </p>
                <p>
                    This is my little space on the internet that I hope won't end up as just another abandoned project on a
                    godforsaken repository somewhere. It serves two purposes:
                </p>
                <ul>
                    <li>
                        Having a technological playground where I can both - improve my skills and use as a creative outlet
                    </li>
                    <li>
                        Use as a virtual journal to share <i>n'importe quoi</i>, the majority of which will be random musings and thoughts
                    </li>
                </ul>
                <p>
                    With that out of the way, let me give you a quick overview of what you can find here:
                </p>
                <ul>
                    <li>
                        The Archive is where you'll find all my posts
                    </li>
                    <li>
                        About, is well, more information about me
                    </li>
                    <li>
                        The Now page is inspired by someone called <a class="highlight" href="https://sive.rs/nowff" target="_blank">Derek Sivers</a>;
                        I don't know him, but I like the idea, even if I'm over a decade late to the party
                    </li>
                </ul>
            </div>
            <h3>Most Recent Updates</h3>
            <div class="recent-updates">
                @if(posts.isEmpty())
                    No recent articles found.
                @else
                    @for(PostModel article: posts)
                        <div class="item">
                            <a href="/posts/${article.getId()}">${article.getTitle()}</a>
                            <span class="breaker"></span>
                            <span class="item-date">${article.getPublishedDate()}</span>
                        </div>
                    @endfor
                @endif
            </div>
        </div>
        <div class="right-column">
            <div class="side-panel">
                <div class="photo">
                    <img class="profile-pic" src="/images/profile_pic.jpg" alt="Dimitar smiling">
                </div>
                <div class="side-panel-text">
                    <div class="description">
                        <p>
                            Technology Generalist
                        </p>
                        <p>
                            15+ years of tinkering with computers professionally
                        </p>
                    </div>
                    <div class="social-links">
                        <p>
                            Find me here:
                        </p>
                        <div class="links-holder">
                            <a href="https://github.com/dimitarOnGithub" target="_blank"><img class="icon" src="/icons/github.png" alt="Github Link"></a>
                            <a href="https://www.linkedin.com/in/dimitarxivanov/" target="_blank"><img class="icon" src="/icons/linkedin.png" alt="LinkedIn Link"></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
`
)