import {Component, inject} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ArticleService} from '@app/features/article/services/article.service';
import {switchMap} from 'rxjs';
import {toSignal} from '@angular/core/rxjs-interop';
import {DatePipe} from '@angular/common';
import {ArticleNavigation} from '@app/features/article/components/article-navigation/article-navigation';

@Component({
  selector: 'app-article',
  imports: [
    DatePipe,
    ArticleNavigation
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
})
export class ArticleComponent {

  private route: ActivatedRoute = inject(ActivatedRoute);
  private articleService: ArticleService = inject(ArticleService);

  private article$ = this.route.params
    .pipe(
      switchMap(
        params => {
          return this.articleService.getById(params['id'])
        }
      )
    );

  article = toSignal(this.article$,
    {
      initialValue : {
        id: 0,
        title: "Loading",
        content: "Loading",
        publishedDate: "loading",
        isADraft: false
      }
    })
}
