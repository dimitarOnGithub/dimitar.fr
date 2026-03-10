import {Component, inject, input} from '@angular/core';
import {ArticleService} from '@app/blog/article/services/article.service';
import {rxResource} from '@angular/core/rxjs-interop';
import {DatePipe} from '@angular/common';
import {ArticleNavigation} from '@app/blog/article/components/article-navigation/article-navigation';

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

  private articleService: ArticleService = inject(ArticleService);

  id = input.required<number>();
  article = rxResource({
    params: () => this.id(),
    stream: ({ params }) => this.articleService.getById(params)
  })
}
