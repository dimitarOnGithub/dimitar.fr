import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Listening } from './listening';

describe('Listening', () => {
  let component: Listening;
  let fixture: ComponentFixture<Listening>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Listening]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Listening);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
