package org.example.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import io.jsondb.annotation.Secret;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

@Document(collection = "tasks", schemaVersion = "1.0")
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

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
