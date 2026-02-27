import { TestBed } from '@angular/core/testing';

import { Recent } from './recent';

describe('Recent', () => {
  let service: Recent;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Recent);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
