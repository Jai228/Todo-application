import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

import { Todo, TodoService } from '../services/todo';
import { TodoDialog } from '../todo-dialog/todo-dialog';

@Component({
  selector: 'app-todo-secition',
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatSlideToggleModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
  ],
  templateUrl: './todo-section.html',
  styleUrl: './todo-secition.scss'
})
export class TodoSection implements OnInit {
  todos: Todo[] = [];
  allTags: string[] = [];

  filterTitle = '';
  filterTag = '';

  dataSource = new MatTableDataSource<Todo>([]);
  displayedColumns: string[] = ['Status', 'title', 'description', 'createdAt', 'updatedAt', 'actions'];

  constructor(private todoService: TodoService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadTodos();
  }

  /** Load todos and extract tags safely */
  loadTodos() {
    this.todoService.getTodos().subscribe({
      next: (data) => {
        // Make sure tags is always defined
        this.todos = data.map(todo => ({
          ...todo,
          tags: todo.tags ?? []
        }));

        this.dataSource.data = this.todos;
        this.extractTags();
        console.log('Loaded todos:', this.todos);
      },
      error: (err) => console.error('Error loading todos', err)
    });
  }

  /** Extract all unique tags */
  extractTags() {
    const tagsSet = new Set<string>();
    this.todos.forEach(todo => (todo.tags ?? []).forEach(tag => tagsSet.add(tag)));
    this.allTags = Array.from(tagsSet);
  }

  /** Apply filter by title and tag */
  applyFilter() {
    const title = this.filterTitle.toLowerCase();
    const tag = this.filterTag;

    this.dataSource.data = this.todos.filter(todo => {
      const matchesTitle = todo.title.toLowerCase().includes(title);
      const matchesTag = tag ? (todo.tags ?? []).includes(tag) : true;
      return matchesTitle && matchesTag;
    });
  }

  /** CRUD operations */
  addTodo(todo: Todo) {
    this.todoService.createTodo(todo).subscribe(() => this.loadTodos());
  }

  updateTodo(todo: Todo) {
    if (!todo.id) return;
    this.todoService.updateTodo(todo.id, todo).subscribe(() => this.loadTodos());
  }

  deleteTodo(todo: Todo) {
    if (!todo.id) return;
    this.todoService.deleteTodo(todo.id).subscribe(() => this.loadTodos());
  }

  toggleCompletion(todo: Todo) {
    if (!todo.id) return;
    const updatedTodo = { ...todo, completed: !todo.completed };
    this.todoService.updateTodo(todo.id, updatedTodo).subscribe(() => this.loadTodos());
  }


  /** Dialog for editing */
  openEditDialog(todo: Todo): void {
    const dialogRef = this.dialog.open(TodoDialog, {
      data: { todo, title: 'Update Todo' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateTodo(result);
      }
    });
  }

  /** Dialog for adding new todo */
  openAddDialog(): void {
    const dialogRef = this.dialog.open(TodoDialog, {
      data: { title: 'Add New Todo' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addTodo(result);
      }
    });
  }
}
