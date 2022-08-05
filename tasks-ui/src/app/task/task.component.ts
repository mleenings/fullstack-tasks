import {Component, OnInit} from '@angular/core';
import {TaskService} from "./task.service";
import {TaskClient} from "./task.client";
import {Task} from "./task";

@Component({
  selector: 'task-component',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css'],
  providers: [TaskService, TaskClient],
})
export class TaskComponent implements OnInit {
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
