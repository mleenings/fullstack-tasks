import {Component, OnInit} from '@angular/core';
import {Task} from './core/task/task';
import {TaskService} from './core/task/task.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [TaskService],
})
export class AppComponent implements OnInit {
  tasks: Task[] = [];
  newTask: Task = new Task();

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.taskService
        .getAllTasks().
        subscribe(
            (tasks) => {
              this.tasks = tasks;
            },
        );
  }

  addTask(task) {
    this.taskService
        .addTask(task)
        .subscribe(
            (newTask) => {
              this.tasks = this.tasks.concat(newTask);
              this.newTask = new Task();
            },
        );
  }

  toggleTaskComplete(task) {
    this.taskService
        .toggleTaskComplete(task)
        .subscribe(
            (updatedTask) => {
              task = updatedTask;
            },
        );
  }

  removeTask(task) {
    this.taskService
        .deleteTaskById(task.id)
        .subscribe(
            (_) => {
              this.tasks = this.tasks.filter((t) => t.id !== task.id);
            },
        );
  }
}
