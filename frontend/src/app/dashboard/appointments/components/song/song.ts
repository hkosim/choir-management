import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormField } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-song',
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatCardModule,
    MatFormField,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './song.html',
  styleUrl: './song.scss',
})
export class Song {
  songForm = null;

  removeSong(){
    console.log('remove')
  }
}
