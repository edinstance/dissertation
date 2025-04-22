package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main class for the backend application.
 */
@SuppressWarnings({"PMD", "checkstyle:hideutilityclassconstructor"})
@SpringBootApplication
@EnableScheduling
public class BackendApplication {

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

}
