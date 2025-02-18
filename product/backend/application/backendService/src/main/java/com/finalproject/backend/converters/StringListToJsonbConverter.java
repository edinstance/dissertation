package com.finalproject.backend.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.backend.config.logging.AppLogger;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.List;

/**
 * This converter converts from a list of strings to jsonb.
 */
@Converter
public class StringListToJsonbConverter implements AttributeConverter<List<String>, String> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List attribute) {
    if (attribute == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      AppLogger.error("Error converting list to jsonb", e);
      throw new IllegalArgumentException("Error converting list to JSON", e);
    }
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    try {
      return objectMapper.readValue(dbData, objectMapper.getTypeFactory()
              .constructCollectionType(List.class, String.class));
    } catch (IOException e) {
      AppLogger.error("Error converting string list to jsonb", e);
      throw new IllegalArgumentException("Error converting JSON to list", e);
    }
  }
}