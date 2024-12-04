package com.finalproject.backend.ConverterTests;

import com.finalproject.backend.converters.TimestampToStringConverter;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimestampToStringConverterTests {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private final TimestampToStringConverter converter = new TimestampToStringConverter();

  @Test
  public void testConvertToDatabaseColumn() throws Exception {
    String dateString = "2024-12-31 23:59:59";
    Timestamp expectedTimestamp = new Timestamp(dateFormat.parse(dateString).getTime());

    Timestamp result = converter.convertToDatabaseColumn(dateString);
    assertEquals(expectedTimestamp, result);
  }

  @Test
  public void testConvertToDatabaseColumn_Null() {
    assertNull(converter.convertToDatabaseColumn(null));
  }

  @Test
  public void testConvertToDatabaseColumn_EmptyString() {
    assertNull(converter.convertToDatabaseColumn(""));
  }

  @Test
  public void testConvertToDatabaseColumn_InvalidFormat() {
    String invalidDateString = "invalid date";
    assertThrows(IllegalArgumentException.class, () -> {
      converter.convertToDatabaseColumn(invalidDateString);
    });
  }

  @Test
  public void testConvertToEntityAttribute() throws Exception {
    String dateString = "2024-12-31 23:59:59";
    Timestamp timestamp = new Timestamp(dateFormat.parse(dateString).getTime());

    String result = converter.convertToEntityAttribute(timestamp);
    assertEquals(dateString, result);
  }

  @Test
  public void testConvertToEntityAttribute_Null() {
    assertNull(converter.convertToEntityAttribute(null));
  }
}