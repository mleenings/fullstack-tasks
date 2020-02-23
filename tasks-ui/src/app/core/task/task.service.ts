import {Injectable} from '@angular/core';
import {Task} from './task';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private baseUrl = 'http://localhost:8080';
  private apiUrl = '/api/tasks/';
  private taskUrl = this.baseUrl + this.apiUrl;

  constructor(private http: HttpClient) {}

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.taskUrl, httpOptions).pipe(
        tap((tasks) => console.log(tasks)),
        catchError(this.handleError([])),
    );
  }

  getTodoById(id: number): Observable<Task> {
    return this.http.get<Task>(this.taskUrl + id, httpOptions).pipe(
        tap((task) => console.log(task)),
        catchError(this.handleError<Task>()),
    );
  }

  addTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.taskUrl, JSON.stringify(task), httpOptions).pipe(
        tap((task) => console.log(task)),
        catchError(this.handleError<Task>()),
    );
  }

  deleteTaskById(id: number): Observable<null> {
    return this.http.delete<null>(this.taskUrl + id, httpOptions).pipe(
        catchError(this.handleError<null>()),
    );
  }

  updateTask(task: Task): Observable<Task> {
    return this.http.put<Task>(this.taskUrl + task.id, JSON.stringify(task), httpOptions).pipe(
        tap((task) => console.log(task)),
        catchError(this.handleError<Task>()),
    );
  }

  toggleTaskComplete(task: Task) {
    task.done = !task.done;
    return this.updateTask(task);
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
