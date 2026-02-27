import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleNavigation } from './article-navigation';

describe('ArticleNavigation', () => {
  let component: ArticleNavigation;
  let fixture: ComponentFixture<ArticleNavigation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleNavigation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleNavigation);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
