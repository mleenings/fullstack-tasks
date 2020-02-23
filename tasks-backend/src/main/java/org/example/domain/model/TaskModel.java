package org.example.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import io.jsondb.annotation.Secret;
import lombok.EqualsAndHashCode;

@Document(collection = "tasks", schemaVersion = "1.0")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskModel {

    @Id
    private String id;

    @Secret
    private String privateKey;

    private String text;
    private boolean done;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
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
