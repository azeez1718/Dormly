import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarmessageComponent } from './sidebarmessage.component';

describe('SidebarmessageComponent', () => {
  let component: SidebarmessageComponent;
  let fixture: ComponentFixture<SidebarmessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarmessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarmessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
