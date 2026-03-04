import { Component } from '@angular/core';
import {MiniPic} from '@app/blog/pages/index/components/mini-pic/mini-pic';

@Component({
  selector: 'app-side-panel',
  imports: [
    MiniPic
  ],
  templateUrl: './side-panel.html',
  styleUrl: './side-panel.scss',
})
export class SidePanel {

}
