import {Component, inject, signal} from '@angular/core';
import {email, form, FormField, required} from '@angular/forms/signals';
import {Location} from '@angular/common';

@Component({
  selector: 'app-contact',
  imports: [
    FormField
  ],
  templateUrl: './contact.html',
  styleUrl: './contact.scss',
})
export class Contact {

  private location = inject(Location);

  contactModel = signal({
    message: '',
    name: '',
    email: ''
  })
  contactForm = form(this.contactModel, (fieldPath) => {
    required(
      fieldPath.message,
      {message: 'You should enter at least some kind of a message, right?'}
    );
    required(
      fieldPath.email,
      {message: 'You forgot to enter your e-mail, how can I reply? :('}
    );
    email(
      fieldPath.email,
      {message: 'That e-mail does not look quite right'}
    );
  });

  onSubmit(event: SubmitEvent){
    event.preventDefault();
    if (this.contactForm.name().touched()) {
      window.location.href = "http://localhost/"
    }
    // TODO: Implement the backend
  }

}
