import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Todo {
  id?: number;
  title: string;
  description?: string;
  tags?: string[];
  dueDate?: string; // ISO string
  createdAt?: string;
  updatedAt?: string;
  completed?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private baseUrl ='http://localhost:8080/api/todos';

  constructor(private http: HttpClient) {}

  getTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(this.baseUrl);
  }

  getTodo(id: number): Observable<Todo> {
    return this.http.get<Todo>(`${this.baseUrl}/${id}`);
  }

  createTodo(todo: Todo): Observable<Todo> {
    return this.http.post<Todo>(this.baseUrl, todo);
  }

  updateTodo(id: number, todo: Todo): Observable<Todo> {
    return this.http.put<Todo>(`${this.baseUrl}/${id}`, todo);
  }

  deleteTodo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchTodos(query: string): Observable<Todo[]> {
    return this.http.get<Todo[]>(`${this.baseUrl}/search?title=${query}`);
  }

  autocompleteTitles(query: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/autocomplete?query=${query}`);
  }
}