import { Component } from '@angular/core';

@Component({
  selector: 'app-mini-pic',
  imports: [],
  templateUrl: './mini-pic.html',
  styleUrl: './mini-pic.scss',
})
export class MiniPic {

  balkanMode: boolean = false;

  switchToBalkanMode() {
    this.balkanMode = true;
  }

  switchToNormalMode() {
    this.balkanMode = false;
  }

}
