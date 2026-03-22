@import fr.dimitar.web.posts.dto.PostModel
@import java.util.Optional

@param PostModel article
@param Optional<PostModel> previous
@param Optional<PostModel> next

@template.base(
    title = "${article.getTitle()}",
    head = @`<link rel="stylesheet" href="css/pages/article.css">`,
    content =  @`
    <div class="article">
        <div class="article-header">
            <div class="article-data">
                <h2>
                    ${article.getTitle()}
                </h2>
                <div class="published-date">
                    ${article.getPublishedDate()}
                </div>
            </div>
        </div>
        <div class="article-content content">
            $unsafe{article.getContent()}
        </div>
    </div>
    <div class="article-navigation">
        @if (previous.isPresent())
            <a class="previous-article" href="/posts/${previous.get().getId()}">
                <h3>${previous.get().getTitle()}</h3>
                <div class="article-date">${previous.get().getPublishedDate()}</div>
            </a>
        @endif
        @if (next.isPresent())
            <a class="next-article" href="/posts/${next.get().getId()}">
                <h3>${next.get().getTitle()}</h3>
                <div class="article-date">${next.get().getPublishedDate()}</div>
            </a>
        @endif
    </div>
`
)
