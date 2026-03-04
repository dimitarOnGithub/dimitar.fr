import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ArticleService} from '@app/blog/article/services/article.service';
import {ArticleData} from '@app/blog/article/models/article.model';
import {tap} from 'rxjs';


@Component({
  selector: 'app-recent',
  imports: [
    RouterLink
  ],
  templateUrl: './recent.html',
  styleUrl: './recent.scss',
})


export class Recent implements OnInit {

  private articleService: ArticleService = inject(ArticleService);

  articles = signal([] as ArticleData[]);
  currentPage = signal(0);
  totalPages: number = 0;

  contentLeft = computed(() => this.currentPage() + 1 <= this.totalPages)

  ngOnInit() {
    // Get the data from the API
    this.articleService.getArticlesByPage(this.currentPage())
      .pipe(
        tap(
          response => {
            this.articles.set(response.content);
            this.totalPages = response.page.totalPages;
          }
        )
      ).subscribe();
  }

  loadNext() {
    // Get the next page of data and append it to the existing array of articles
    this.articleService.getArticlesByPage(this.currentPage() + 1)
      .pipe(
        tap(
          (response) => {
            this.articles.update(
              currentValue => currentValue.concat(response.content)
            )
          }
        )
      ).subscribe();
    this.currentPage.update(() => this.currentPage() + 1)
  }
}

