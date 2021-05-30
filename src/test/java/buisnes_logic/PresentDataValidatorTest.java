package buisnes_logic;

import dao.DaoException;
import dao.DaoFactory;
import dao.jdbc.JdbcWorker;
import data.Present;
import data.Worker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PresentDataValidatorTest {

    private static final DaoFactory factory = DaoFactory.getInstance();

    @BeforeEach
    public  void initDatabase() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = factory.getConnection();
            String sql = getSqlFileQueries("src/test/resources/schema.sql");
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterEach
    public  void clearDataBase() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection  = factory.getConnection();
            String sql = getSqlFileQueries("src/test/resources/clearDB.sql");
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSqlFileQueries(String fileRoute) throws IOException {
        StringBuilder queries = new StringBuilder();
        File file = new File(fileRoute);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String string;
        while ((string = br.readLine()) != null) {
            queries.append(string);
        }
        return queries.toString();
    }

    @Test
    public void shouldCreateReceiveInfotWithValidData() throws DaoException {
        new JdbcWorker().create(new Worker("",""));
        assertDoesNotThrow(() -> PresentDataValidator.createPresent(new Present(1,"",true)));
    }

    @Test
    public void shouldNotCreateReceiveInfoWithNotValidData() throws DaoException {
        new JdbcWorker().create(new Worker("",""));
        assertThrows(DaoException.class,() -> PresentDataValidator.createPresent(new Present(2,"",true)));
    }

}
