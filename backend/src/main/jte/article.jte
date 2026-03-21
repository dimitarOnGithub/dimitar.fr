@import fr.dimitar.web.posts.dto.PostModel

@param PostModel article

@template.base(
    title = "${article.getTitle()}",
    head = @`" "`,
    content =  @``
)
