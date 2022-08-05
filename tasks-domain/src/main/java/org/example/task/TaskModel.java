package org.example.task;

import org.example.util.CustomSerializerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import io.jsondb.annotation.Secret;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "tasks", schemaVersion = "1.0")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskModel {

  @Id
  String id;
  @Secret
  String privateKey;
  String text;
  boolean done;

  @SneakyThrows
  @Override
  public String toString() {
    final ObjectMapper om = new ObjectMapper();
    om.setSerializerProvider(new CustomSerializerProvider());
    return om.writeValueAsString(this);
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
