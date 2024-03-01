import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { PopupComponent } from './popup.component';

describe('PopupComponent', () => {
  let component: PopupComponent;
  let fixture: ComponentFixture<PopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PopupComponent],
      imports: [MatDialogModule],
      providers: [
        { provide: MatDialogRef, useValue: {}},
      ],
    });
    fixture = TestBed.createComponent(PopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
