import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {inject, Injectable} from '@angular/core'
import {ArticleData} from '@app/blog/article/models/article.model';
import {environment} from '@env/environment';
import {PaginatedResponse} from '@app/shared/models/paginated.model';
import {Observable} from 'rxjs';


@Injectable({providedIn: 'root'})
export class ArticleService {

  private readonly apiUrl = `${environment.apiUrl}/posts`;
  private http= inject(HttpClient);
  private httpHeaders = new HttpHeaders(
    {
      "Allow-Cross-Origin": "http://localhost:8080",
      "Access-Control-Allow-Origin": "http://localhost:8080"
    }
  )

  getArticlesByPage(page: number): Observable<PaginatedResponse<ArticleData>>  {
    let httpParams: HttpParams = new HttpParams().set("page", page);
    console.log(httpParams)
    return this.http.get<PaginatedResponse<ArticleData>>(`${this.apiUrl}`, {
      headers: this.httpHeaders,
      params: httpParams
    });
  }

  getById(articleId: number) {
    return this.http.get<ArticleData>(`${this.apiUrl}/${articleId}`, {
      headers: this.httpHeaders
    });
  }

}
