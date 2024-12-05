package com.finalproject.backend.ConverterTests;

import com.finalproject.backend.converters.StringListToJsonbConverter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringListToJsonbConverterTests {

  private final StringListToJsonbConverter converter = new StringListToJsonbConverter();

  @Test
  public void testConvertToDatabaseColumn() {
    List<String> list = Arrays.asList("image1.jpg", "image2.jpg");
    String json = converter.convertToDatabaseColumn(list);
    assertEquals("[\"image1.jpg\",\"image2.jpg\"]", json);
  }

  @Test
  public void testConvertToDatabaseColumnNull() {
    String json = converter.convertToDatabaseColumn(null);
    assertNull(json);
  }

  @Test
  public void testConvertToEntityAttribute() {
    String json = "[\"image1.jpg\",\"image2.jpg\"]";
    List<String> list = converter.convertToEntityAttribute(json);
    assertEquals(Arrays.asList("image1.jpg", "image2.jpg"), list);
  }

  @Test
  public void testConvertToEntityAttributeNull() {
    List<String> list = converter.convertToEntityAttribute(null);
    assertNull(list);
  }

  @Test
  public void testConvertToEntityAttributeInvalidJson() {
    String invalidJson = "invalid json";
    assertThrows(IllegalArgumentException.class, () -> {
      converter.convertToEntityAttribute(invalidJson);
    });
  }
}