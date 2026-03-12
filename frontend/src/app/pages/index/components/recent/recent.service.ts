import {inject, Injectable} from '@angular/core';
import {environment} from '@env/environment';
import {HttpClient} from '@angular/common/http';
import {ArticleCacheService} from '@app/article/article-cache.service';
import {ArticleData} from '@app/article/article.model';
import {PaginatedResponse} from '@app/shared/models/paginated.model';
import {map, tap} from 'rxjs';

@Injectable({providedIn: 'root'})
export class RecentService {

  private readonly apiUrl = `${environment.apiUrl}/posts`;
  private http= inject(HttpClient);
  private articlesCache = inject(ArticleCacheService)

  getArticlesByPage(pageNumber: number, pageSize: number) {
    return this.http.get<PaginatedResponse<ArticleData>>(
      `${this.apiUrl}`,
      {
        params: {
          page: pageNumber,
          pageSize: pageSize
        }
      }
    )
      .pipe(
        tap(data => {
          this.articlesCache.addMany(data.content)
        }
        )
      )
  }

}
