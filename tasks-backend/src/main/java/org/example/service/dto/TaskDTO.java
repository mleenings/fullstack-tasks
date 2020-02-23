package org.example.service.dto;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import org.example.domain.model.TaskModel;

// Info: Lombok not working perfectly with the reflection of Spring,
// so we need to create real getter and setter and no @Data-Annotation
@EqualsAndHashCode
public class TaskDTO {

    private String id;
    private String text;
    private boolean done;

    public TaskDTO() {
        // Empty constructor needed for Jackson.
    }

    public TaskDTO(TaskModel model) {
        if (model != null) {
            id = model.getId();
            text = model.getText();
            done = model.isDone();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
