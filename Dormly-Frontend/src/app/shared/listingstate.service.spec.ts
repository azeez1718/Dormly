import { TestBed } from '@angular/core/testing';

import { ListingstateService } from './listingstate.service';

describe('ListingstateService', () => {
  let service: ListingstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListingstateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
