import {Component, computed, inject, input} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ArticleService} from '@app/article/article.service';
import {rxResource} from '@angular/core/rxjs-interop';
import {catchError, of} from 'rxjs';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-article-navigation',
  imports: [
    RouterLink,
    DatePipe
  ],
  templateUrl: './article-navigation.html',
  styleUrl: './article-navigation.scss',
})
export class ArticleNavigation {

  private articleService: ArticleService = inject(ArticleService);

  id = input.required<number>();
  currentArticleId = computed(() => Number(this.id()));
  previousArticle = rxResource({
    params: () => this.currentArticleId(),
    stream: ({params}) => this.articleService.getArticleById(params - 1)
      .pipe(
        catchError(() => of(null))
      )
  })
  nextArticle = rxResource({
    params: () => this.currentArticleId(),
    stream: ({params}) => this.articleService.getArticleById(params + 1)
      .pipe(
        catchError(() => of(null))
      )
  })

}
