import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodoSecition } from './todo-section';

describe('TodoSecition', () => {
  let component: TodoSecition;
  let fixture: ComponentFixture<TodoSecition>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TodoSecition]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TodoSecition);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
