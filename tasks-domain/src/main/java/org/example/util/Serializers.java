package org.example.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class Serializers extends JsonSerializer<Object> {
  public static final JsonSerializer<Object> EMPTY_STRING_SERIALIZER_INSTANCE =
      new EmptyStringSerializer();
  public static final JsonSerializer<Object> EMPTY_ARRAY_SERIALIZER_INSTANCE =
      new EmptyArraySerializer();

  public Serializers() {
    // default constructor
  }

  @Override
  public void serialize(
      Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeString("");
  }

  private static class EmptyStringSerializer extends JsonSerializer<Object> {
    public EmptyStringSerializer() {
      // default constructor
    }

    @Override
    public void serialize(
        Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      jsonGenerator.writeString("");
    }
  }

  private static class EmptyArraySerializer extends JsonSerializer<Object> {
    public EmptyArraySerializer() {
      // default constructor
    }

    @Override
    public void serialize(
        Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      jsonGenerator.writeString("[]");
    }
  }
}
