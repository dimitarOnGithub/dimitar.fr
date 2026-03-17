import {Component, computed, inject, signal} from '@angular/core';
import {form, FormField, required} from '@angular/forms/signals';
import {GuestbookService} from '@app/guestbook/guestbook.service';
import {GuestbookEntry} from '@app/guestbook/models/guestbookentry.model';
import {EntriesList} from '@app/guestbook/components/entries-list/entries-list';

@Component({
  selector: 'app-guestbook',
  imports: [
    FormField,
    EntriesList
  ],
  templateUrl: './guestbook.html',
  styleUrl: './guestbook.scss',
})
export class Guestbook {

  private guestbookService: GuestbookService = inject(GuestbookService);

  guestBookModel = signal<GuestbookEntry>({
    content: '',
    username: '',
    alias: '',
    userWebsite: ''
  })

  guestBookForm = form(this.guestBookModel, (fieldPath) => {
    required(fieldPath.content);
  })

  formSubmitted = computed(() => this.guestbookService.formSubmitted());
  errorMessage = signal<string | undefined>(undefined);

  onSubmit(event: SubmitEvent){
    event.preventDefault();
    if (this.guestBookForm.alias().touched()) {
      window.location.href = "http://localhost/"
      return;
    }
    this.guestbookService.publishEntry(this.guestBookModel())
      .subscribe({
        next: response => {
          if (response.status == 201) {
            this.guestbookService.formSubmitted.set(true);
          }
        },
        error: err => {
          this.errorMessage.set(`Something is not quite right, sorry. Can you please try again later?`)
        }
      });
  }

}
