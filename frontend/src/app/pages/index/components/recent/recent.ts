import {Component, computed, inject, Signal, signal, WritableSignal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {RecentService} from '@app/pages/index/components/recent/recent.service';
import {rxResource} from '@angular/core/rxjs-interop';
import {map, tap} from 'rxjs';
import {ArticleData} from '@app/article/article.model';

@Component({
  selector: 'app-recent',
  imports: [
    RouterLink
  ],
  templateUrl: './recent.html',
  styleUrl: './recent.scss',
})

export class Recent {

  private recentService: RecentService = inject(RecentService);

  visibleCount: WritableSignal<number> = signal(5)
  currentPage: WritableSignal<number> = signal(0)
  totalPages = signal(0)
  contentLeft = computed(() => this.currentPage() <= this.totalPages() + 1)
  articles = signal([] as ArticleData[])
  pageResource = rxResource({
    params: () => this.currentPage(),
    stream: ({params}) => {
      return this.recentService.getArticlesByPage(params, this.visibleCount()).pipe(
        tap(
          data => {
            this.totalPages.set(data.page.totalPages);
            this.articles.update(current => [...current, ...data.content])
          }
        )
      )
    }
  })

  loadNext(): void {
    this.currentPage.update(page => page + 1)
  }

}

