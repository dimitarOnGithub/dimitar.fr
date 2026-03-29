@import java.util.List
@import java.util.Map
@import fr.dimitar.web.posts.dto.PostModel

@param Map<Integer, List<PostModel>> postsMap


@template.base(
title = "Blog Archive",
head = @`<link rel="stylesheet" href="/static/css/pages/archive.css">`,
content = @`
    <div class="archive-container">
        @if(postsMap.isEmpty())
            There doesn't seem to be any articles posted at all?
        @else
            @for(var entry: postsMap.entrySet())
                <div class="year-container">
                    <h1>${entry.getKey()}</h1>
                    @for(var article: entry.getValue())
                        <div class="item">
                            <a href="/posts/${article.getId()}">${article.getTitle()}</a>
                            <span class="breaker"></span>
                            <span class="item-date">${article.formatPublishedDate("dd MMMM")}</span>
                        </div>
                    @endfor
                </div>
            @endfor
        @endif
    </div>
`
)