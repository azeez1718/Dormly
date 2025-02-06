import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListingcardComponent } from './listingcard.component';

describe('ListingcardComponent', () => {
  let component: ListingcardComponent;
  let fixture: ComponentFixture<ListingcardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListingcardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListingcardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
