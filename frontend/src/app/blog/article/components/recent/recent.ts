import {Component, computed, inject, Signal, signal, WritableSignal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ArticleService} from '@app/blog/article/services/article.service';
import {ArticleData} from '@app/blog/article/models/article.model';


@Component({
  selector: 'app-recent',
  imports: [
    RouterLink
  ],
  templateUrl: './recent.html',
  styleUrl: './recent.scss',
})

export class Recent {

  private articleService: ArticleService = inject(ArticleService);

  visibleCount: WritableSignal<number> = signal(5)
  articles: Signal<ArticleData[]> = computed(() => {
      return this.articleService.articles().slice(0, this.visibleCount())
    }
  )
  contentLeft: Signal<boolean> = computed(() => this.articles().length < this.articleService.articles().length)

  loadNext(): void {
    this.visibleCount.update(visible => visible + 5)
  }
}

