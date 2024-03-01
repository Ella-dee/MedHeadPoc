import { TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { PopupService } from './popup.service';

describe('PopupService', () => {
  let service: PopupService;

  beforeEach(() => {
    TestBed.configureTestingModule({ 
      imports: [MatDialogModule],     
      providers: [
        { provide: MatDialogRef, useValue: {}},
      ],
    });
    service = TestBed.inject(PopupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
