import {Component, computed, inject, input} from '@angular/core';
import {ArticleService} from '@app/article/article.service';
import {rxResource} from '@angular/core/rxjs-interop';
import {DatePipe} from '@angular/common';
import {ArticleNavigation} from '@app/article/components/article-navigation/article-navigation';

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
  articleId = computed(() => Number(this.id()))
  article = rxResource({
    params: () => this.articleId(),
    stream: ({ params }) => this.articleService.getArticleById(params)
  })
}
