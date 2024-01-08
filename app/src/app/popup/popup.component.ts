import { Component } from '@angular/core';
import { PopupService } from './popup.service';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  template: `
  <button (click)="openPopup()">Open Popup</button>
`,
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent {
  constructor(private popupService: PopupService) {}

  openPopup() {
    this.popupService.openPopup();
  }
  closeDialog(){
    this.popupService.closeDialog();
  }
}
