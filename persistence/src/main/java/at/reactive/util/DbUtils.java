package at.reactive.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import oracle.jdbc.OracleConnection;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@UtilityClass
public class DbUtils {

    @SneakyThrows
    public OracleConnection getOracleConnectionFromDataSource(DataSource dataSource) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        return connection.unwrap(OracleConnection.class);
    }
}
