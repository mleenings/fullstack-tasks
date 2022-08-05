package org.example.web.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.example.util.CustomSerializerProvider;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskVM {

    String id;
    String text;
    boolean done;

    @SneakyThrows
    @Override
    public String toString() {
      ObjectMapper om = new ObjectMapper();
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
