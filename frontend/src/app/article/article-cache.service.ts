import {Injectable} from '@angular/core';
import {ArticleData} from '@app/article/article.model';

@Injectable({
  providedIn: 'root',
})
export class ArticleCacheService {

  private articleCache = new Map<number, ArticleData>();

  addArticle(article: ArticleData) {
    this.articleCache.set(
      article.id,
      article
    )
  }

  getArticleById(articleId: number): ArticleData | undefined {
    return this.articleCache.get(articleId);
  }

  addMany(articles: Array<ArticleData>){
    for(let article of articles) {
      this.articleCache.set(article.id, article);
    }
  }

}
