import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';
import { ModalContent } from '../../../models/modal-content.model';

@Component({
  selector: 'app-modal-template',
  imports: [
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
  templateUrl: './modal-template.html',
  styleUrl: './modal-template.scss',
})
export class ModalTemplate {
  data: ModalContent = inject(MAT_DIALOG_DATA);
}
