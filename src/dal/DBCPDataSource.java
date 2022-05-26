package dal;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();
    private static DBCPDataSource single_instance = null;

    static {
        ds.setUrl("jdbc:sqlserver://10.176.111.31;database=SOSU;");
        ds.setUsername("CSe21B_30");
        ds.setPassword("CSe21B_30");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static DBCPDataSource getInstance()
    {
        if (single_instance == null)
            single_instance = new DBCPDataSource();

        return single_instance;
    }
}
