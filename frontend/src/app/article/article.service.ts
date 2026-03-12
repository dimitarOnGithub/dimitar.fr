import {HttpClient} from "@angular/common/http";
import {inject, Injectable} from '@angular/core'
import {ArticleData} from '@app/article/article.model';
import {environment} from '@env/environment';
import {of, shareReplay, tap} from 'rxjs';
import {ArticleCacheService} from '@app/article/article-cache.service';


@Injectable({providedIn: 'root'})
export class ArticleService {

  private readonly apiUrl = `${environment.apiUrl}/posts`;
  private http= inject(HttpClient);
  private articlesCache = inject(ArticleCacheService)

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

  getMultipleArticles(articlesId: Array<number>) {
    const articlesMap = new Map();

  }
}
