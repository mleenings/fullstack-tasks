import {AppComponent} from "../app.component";
import {TaskComponent} from "./task.component";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [TaskComponent],
  imports: [
    FormsModule
  ],
  exports: [
    TaskComponent
  ]
})

export class TaskModule {}
