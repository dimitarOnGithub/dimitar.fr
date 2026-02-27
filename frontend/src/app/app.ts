import { Component, signal } from '@angular/core';
import {Header} from '@app/shared/components/header/header';
import {RouterOutlet} from '@angular/router';
import {Footer} from '@app/shared/components/footer/footer';

@Component({
  selector: 'app-root',
  imports: [Header, RouterOutlet, Footer],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('frontend');

}
