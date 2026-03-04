import { Routes } from '@angular/router';
import {ArticleComponent} from '@app/blog/article/article.component';

export const routes: Routes = [
  {
    path: '',
    title: "Dimitar on the Internet",
    loadChildren: () => import('@app/blog/pages/index/index.routes').then(m => m.indexRoutes)
  },
  {
    path: 'archive',
    title: 'Blog Archive',
    loadChildren: () => import('@app/blog/pages/archive/archive.routes').then(m => m.archiveRoutes)
  },
  {
    path: 'about',
    title: "About Dimitar",
    loadChildren: () => import('@app/blog/pages/about/about.routes').then(m => m.aboutRoutes),
  },
  {
    path: 'now',
    title: "Dimitar's Now",
    loadChildren: () => import('@app/blog/pages/now/now.routes').then(m => m.nowRoutes)
  },
  {
    path: 'posts/:id',
    component: ArticleComponent
  }
];
