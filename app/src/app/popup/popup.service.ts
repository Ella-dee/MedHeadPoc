import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupComponent } from './popup.component';

@Injectable({
  providedIn: 'root',
})
export class PopupService {
  constructor(private dialog: MatDialog) {}

  openPopup() {
    this.dialog.open(PopupComponent);
  }

  closeDialog(){
    this.dialog.closeAll();    
    window.location.reload();
  }
}