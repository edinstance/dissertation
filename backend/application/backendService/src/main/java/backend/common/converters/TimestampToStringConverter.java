package backend.common.converters;

import backend.common.config.logging.AppLogger;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This converter converts between a String and a Timestamp for JPA entity attributes.
 */
@Converter
public class TimestampToStringConverter implements AttributeConverter<String, Timestamp> {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * Converts a String attribute to a Timestamp for storing in the database.
   *
   * @param attribute The String attribute to convert.
   *
   * @return The converted Timestamp, or null if the attribute is null or empty.
   * @throws IllegalArgumentException if the String cannot be parsed into a Timestamp.
   */
  @Override
  public Timestamp convertToDatabaseColumn(String attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return null;
    }
    try {
      return new Timestamp(dateFormat.parse(attribute).getTime());
    } catch (ParseException e) {
      AppLogger.error("Error while parsing timestamp", e);
      throw new IllegalArgumentException("Error converting String to Timestamp", e);
    }
  }

  /**
   * Converts a Timestamp from the database to a String attribute.
   *
   * @param dbData The Timestamp from the database.
   *
   * @return The converted String, or null if the dbData is null.
   */
  @Override
  public String convertToEntityAttribute(Timestamp dbData) {
    if (dbData == null) {
      return null;
    }
    return dateFormat.format(dbData);
  }
}