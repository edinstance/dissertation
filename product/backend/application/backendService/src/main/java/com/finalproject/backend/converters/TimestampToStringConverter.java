package com.finalproject.backend.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Converter
public class TimestampToStringConverter implements AttributeConverter<String, Timestamp> {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public Timestamp convertToDatabaseColumn(String attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return null;
    }
    try {
      return new Timestamp(dateFormat.parse(attribute).getTime());
    } catch (ParseException e) {
      throw new IllegalArgumentException("Error converting String to Timestamp", e);
    }
  }

  @Override
  public String convertToEntityAttribute(Timestamp dbData) {
    if (dbData == null) {
      return null;
    }
    return dateFormat.format(dbData);
  }
}