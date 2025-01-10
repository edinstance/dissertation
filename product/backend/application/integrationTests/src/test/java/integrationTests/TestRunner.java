package integrationTests;

import integrationTests.Cognito.CognitoUtilities;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class TestRunner {

  @BeforeAll
  public static void seedCognito() {
    CognitoUtilities cognitoUtilities = new CognitoUtilities();
    cognitoUtilities.createUser("test1@test.com", "TestPassword1!");
    cognitoUtilities.loginUser("test1@test.com", "TestPassword1!");
  }

  @After
  public void tearDown() throws SQLException {

    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "password")) {

      String query = "DELETE FROM items CASCADE";
      Statement statement = connection.createStatement();
      statement.execute(query);

      query = "DELETE FROM user_details CASCADE";
      statement = connection.createStatement();
      statement.execute(query);

      query = "DELETE FROM users CASCADE";
      statement.execute(query);
    }
  }

  @AfterAll
  public static void tearDownCognito() {
    CognitoUtilities cognitoUtilities = new CognitoUtilities();
    cognitoUtilities.deleteUser("test1@test.com");
  }

}
