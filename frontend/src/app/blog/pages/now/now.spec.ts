import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NowPage } from './now';

describe('NowPage', () => {
  let component: NowPage;
  let fixture: ComponentFixture<NowPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NowPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NowPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
