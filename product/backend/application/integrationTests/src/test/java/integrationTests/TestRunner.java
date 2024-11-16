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

            String selectQuery = "DELETE FROM users CASCADE";
            Statement statement = connection.createStatement();
            statement.execute(selectQuery);
        }
    }
}
