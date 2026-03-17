import { Routes } from '@angular/router';
import {ArticleComponent} from '@app/article/article.component';
import {Contact} from '@app/contact/contact';
import {Guestbook} from '@app/guestbook/guestbook';

export const routes: Routes = [
  {
    path: '',
    title: "Dimitar on the Internet",
    loadChildren: () => import('@app/pages/index/index.routes').then(m => m.indexRoutes)
  },
  {
    path: 'archive',
    title: 'Blog Archive',
    loadChildren: () => import('@app/pages/archive/archive.routes').then(m => m.archiveRoutes)
  },
  {
    path: 'about',
    title: "About Dimitar",
    loadChildren: () => import('@app/pages/about/about.routes').then(m => m.aboutRoutes),
  },
  {
    path: 'now',
    title: "Dimitar's Now",
    loadChildren: () => import('@app/pages/now/now.routes').then(m => m.nowRoutes)
  },
  {
    path: 'posts/:id',
    component: ArticleComponent
  },
  {
    path: 'contact',
    component: Contact
  },
  {
    path: 'guestbook',
    component: Guestbook
  }
];
