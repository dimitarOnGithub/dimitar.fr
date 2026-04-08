@import fr.dimitar.web.posts.dto.PostModel

@param PostModel article
@param PostModel previous
@param PostModel next

@template.base(
    title = article.getTitle(),
    head = @`<link rel="stylesheet" href="/static/css/pages/article.css">`,
    content =  @`
    <div class="article">
        <div class="article-header">
            <div class="article-data">
                <h2>
                    ${article.getTitle()}
                </h2>
                <div class="published-date">
                    ${article.formatPublishedDate("dd MMM, yyyy")}
                </div>
            </div>
        </div>
        <div class="article-content content">
            $unsafe{article.getContent()}
        </div>
    </div>
    <div class="article-navigation">
        @if (previous != null)
            <a class="previous-article" href="/posts/${previous.getId()}">
                <h3>${previous.getTitle()}</h3>
                <div class="article-date">${previous.formatPublishedDate("dd MMM, yyyy")}</div>
            </a>
        @endif
        @if (next != null)
            <a class="next-article" href="/posts/${next.getId()}">
                <h3>${next.getTitle()}</h3>
                <div class="article-date">${next.formatPublishedDate("dd MMM, yyyy")}</div>
            </a>
        @endif
    </div>
`
)
