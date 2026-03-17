import {inject, Injectable, signal} from '@angular/core';
import {environment} from '@env/environment';
import {HttpClient} from '@angular/common/http';
import {PaginatedResponse} from '@app/shared/models/paginated.model';
import {GuestbookEntry} from '@app/guestbook/models/guestbookentry.model';

@Injectable({
  providedIn: 'root',
})
export class GuestbookService {

  private readonly apiUrl = `${environment.apiUrl}/guestbook`;
  private http= inject(HttpClient);
  formSubmitted = signal<boolean>(false);

  getEntriesByPage(pageNumber: number, pageSize: number) {
    return this.http.get<PaginatedResponse<GuestbookEntry>>(
      `${this.apiUrl}`,
      {
        params: {
          page: pageNumber,
          pageSize: pageSize
        }
      }
    )
  }

  publishEntry(entry: GuestbookEntry) {
    return this.http.post(`${this.apiUrl}`, entry, {observe: "response"})
  }
}
