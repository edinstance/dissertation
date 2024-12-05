package integrationTests;

import io.cucumber.java.After;
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
}
