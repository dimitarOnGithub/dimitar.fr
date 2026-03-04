import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniPic } from './mini-pic';

describe('MiniPic', () => {
  let component: MiniPic;
  let fixture: ComponentFixture<MiniPic>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiniPic]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiniPic);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
