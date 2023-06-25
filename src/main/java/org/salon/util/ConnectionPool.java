package org.salon.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final BasicDataSource dataSource = new BasicDataSource();

    public static Connection getConnection() throws SQLException, IOException {
        try{
            Class.forName("com.postgresql.jdbc.driver").newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }

        ConfigManager config = ConfigManager.getInstance();

        String url = config.getProperty(ConfigManager.DB_URL);
        String host = config.getProperty(ConfigManager.DB_HOST);
        String port = config.getProperty(ConfigManager.DB_PORT);
        String table = config.getProperty(ConfigManager.DB_TABLE);
        String user = config.getProperty(ConfigManager.DB_USER);
        String password = config.getProperty(ConfigManager.DB_PASSWORD);

        dataSource.setUrl(url+host+":"+port+"/"+table);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(5);
        //dataSource.setDriverClassName("com.postgresql.jdbc.driver");

        return dataSource.getConnection();
    }

    public static void closePool() throws SQLException {
        dataSource.close();
    }
}
