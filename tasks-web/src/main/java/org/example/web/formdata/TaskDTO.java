package org.example.web.formdata;

import com.google.gson.Gson;
import org.example.domain.model.TaskModel;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

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

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
