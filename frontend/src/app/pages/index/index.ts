import { Component } from '@angular/core';
import {SidePanel} from './components/side-panel/side-panel';
import {Recent} from '@app/pages/index/components/recent/recent';


@Component({
  selector: 'app-index',
  imports: [
    SidePanel,
    Recent,
  ],
  templateUrl: './index.html',
  styleUrl: './index.scss',
})
export class IndexPage {

}
