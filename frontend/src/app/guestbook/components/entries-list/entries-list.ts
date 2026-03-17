import {Component, computed, inject, signal, WritableSignal} from '@angular/core';
import {rxResource} from '@angular/core/rxjs-interop';
import {tap} from 'rxjs';
import {GuestbookEntry} from '@app/guestbook/models/guestbookentry.model';
import {GuestbookService} from '@app/guestbook/guestbook.service';

@Component({
  selector: 'app-entries-list',
  imports: [],
  templateUrl: './entries-list.html',
  styleUrl: './entries-list.scss',
})
export class EntriesList {

  private guestbookService: GuestbookService = inject(GuestbookService);

  visibleCount: WritableSignal<number> = signal(10)
  currentPage: WritableSignal<number> = signal(0)
  totalPages = signal(0)
  contentLeft = computed(() => this.currentPage() + 1 < this.totalPages())
  entries = signal([] as GuestbookEntry[])

  pageResource = rxResource({
    params: () => this.currentPage(),
    stream: ({params}) => {
      return this.guestbookService.getEntriesByPage(params, this.visibleCount()).pipe(
        tap(
          data => {
            this.totalPages.set(data.page.totalPages);
            this.entries.update(current => [...current, ...data.content])
          }
        )
      )
    }
  });

  loadNext(): void {
    this.currentPage.update(page => page + 1)
  }


}
