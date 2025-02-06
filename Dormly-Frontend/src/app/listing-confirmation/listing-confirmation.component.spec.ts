import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListingConfirmationComponent } from './listing-confirmation.component';

describe('ListingConfirmationComponent', () => {
  let component: ListingConfirmationComponent;
  let fixture: ComponentFixture<ListingConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListingConfirmationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListingConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
