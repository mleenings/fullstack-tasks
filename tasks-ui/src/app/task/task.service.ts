import {Injectable} from '@angular/core';
import {Task} from './task';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {TaskClient} from "./task.client";

@Injectable({
  providedIn: 'root',
})
export class TaskService {

  constructor(private taskClient: TaskClient) {}

  getAllTasks(): Observable<Task[]> {
    return this.taskClient.getAllTasks();
  }

  getTodoById(id: number): Observable<Task> {
    return this.taskClient.getTodoById(id);
  }

  addTask(task: Task): Observable<Task> {
    return this.taskClient.addTask(task);
  }

  deleteTaskById(id: number): Observable<null> {
    return this.taskClient.deleteTaskById(id);
  }

  updateTask(task: Task): Observable<Task> {
    return this.taskClient.updateTask(task);
  }

  toggleTaskComplete(task: Task) {
    task.done = !task.done;
    return this.updateTask(task);
  }
}
