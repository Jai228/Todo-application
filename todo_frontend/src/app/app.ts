import { Component } from '@angular/core';
import { TodoSection } from './todo-section/todo-section';

@Component({
  selector: 'app-root',
  imports: [TodoSection],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'todo_frontend';

}
