import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {inject, Injectable} from '@angular/core'
import {ArticleData} from '@app/blog/article/models/article.model';
import {environment} from '@env/environment';
import {toSignal} from '@angular/core/rxjs-interop';


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
  private articles$ = this.http.get<ArticleData[]>(
    `${this.apiUrl}`,
    { headers: this.httpHeaders }
  );
  articles = toSignal(this.articles$, { initialValue: [] })

  getById(articleId: number) {
    return this.http.get<ArticleData>(`${this.apiUrl}/${articleId}`, {
      headers: this.httpHeaders
    });
  }

}
