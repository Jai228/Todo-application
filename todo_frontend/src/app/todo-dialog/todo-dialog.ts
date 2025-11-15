import { Component,Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import { Todo } from '../services/todo';  
import {MatChipInputEvent, MatChipsModule} from '@angular/material/chips';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-todo-dialog',
  imports: [ MatFormFieldModule,CommonModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogContent,
    MatDialogActions,
    MatChipsModule,MatIconModule],
  templateUrl: './todo-dialog.html',
  styleUrl: './todo-dialog.scss'
})
export class TodoDialog {
  
  constructor(
    public dialogRef: MatDialogRef<TodoDialog>,
    @Inject(MAT_DIALOG_DATA)  data: { todo: Todo , tittle:string }
  ) {
      this.tittle= data.tittle;
    this.todo = { ...data.todo }; 
  }
  tittle:string= '';
  todo: Todo;
 
  onSave(): void {
    this.dialogRef.close(this.todo); // return updated data to parent
  }

  onCancel(): void {
    this.dialogRef.close(); // just close without returning anything
  }
  

  addTag(event: MatChipInputEvent): void {
  const value = (event.value || '').trim();
  if (!this.todo.tags) this.todo.tags = [];

  if (value && !this.todo.tags.includes(value)) {
    this.todo.tags.push(value);
  }
  event.chipInput!.clear();
}

removeTag(index: number): void {
  if (!this.todo.tags) return;
  if (index >= 0) {
    this.todo.tags.splice(index, 1);
  }
}

}
