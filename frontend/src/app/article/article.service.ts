import {HttpClient} from "@angular/common/http";
import {inject, Injectable} from '@angular/core'
import {ArticleData} from '@app/article/article.model';
import {environment} from '@env/environment';
import {of, shareReplay, tap} from 'rxjs';
import {ArticleCacheService} from '@app/article/article-cache.service';
import {ArticleRelation} from '@app/article/models/ArticleRelation';


@Injectable({providedIn: 'root'})
export class ArticleService {

  private readonly apiUrl = `${environment.apiUrl}/posts`;
  private http= inject(HttpClient);
  private articlesCache = inject(ArticleCacheService)
  private articleRelationsMap = new Map<number, ArticleRelation>();

  getArticleById(id: number) {
    const articleFromCache = this.articlesCache.getArticleById(id);
    if(articleFromCache){
      return of(articleFromCache);
    }
    return this.http.get<ArticleData>(`${this.apiUrl}/${id}`)
      .pipe(
        tap(
          data => this.articlesCache.addArticle(data)
        ),
        shareReplay(1)
      )
  }

  getPreviousArticle(id: number) {
    const previousArticleId = this.articleRelationsMap.get(id)?.previousArticleId();
    if(previousArticleId) {
      const cachedArticleData = this.articlesCache.getArticleById(previousArticleId)
      if(cachedArticleData) {
        return of(cachedArticleData);
      }
    }
    return this.http.get<ArticleData>(`${this.apiUrl}/${id}/previous`)
      .pipe(
        tap(
          data => {
            this.articlesCache.addArticle(data)
            const relationship: ArticleRelation = this.articleRelationsMap.get(id) ?? new ArticleRelation(id);
            relationship.setPreviousIfEmpty(data.id);
            this.articleRelationsMap.set(id, relationship);
          }
        )
      )
  }

  getNextArticle(id: number) {
    const nextArticleId = this.articleRelationsMap.get(id)?.nextArticleId()
    if(nextArticleId) {
      const cachedArticleData = this.articlesCache.getArticleById(nextArticleId)
      if(cachedArticleData) {
        return of(cachedArticleData);
      }
    }
    return this.http.get<ArticleData>(`${this.apiUrl}/${id}/next`)
      .pipe(
        tap(
          data => {
            this.articlesCache.addArticle(data)
            const relationship: ArticleRelation = this.articleRelationsMap.get(id) ?? new ArticleRelation(id);
            relationship.setNextIfEmpty(data.id);
            this.articleRelationsMap.set(id, relationship);
          }
        )
      )
  }

}
