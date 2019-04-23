package org.openjfx.Configuration;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private static final String ROOT_PATH = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("application.properties"))
            .getPath().replaceAll("%20", " ");
    private static Properties property;

    static {
        property = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ROOT_PATH);
            property.load(fis);
            LOGGER.log(Level.INFO, "Application properties file were successful downloaded!");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error: Failed to get application properties file!", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: IO Exception!", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error: Failed to close file!", e);
                }
            }
        }
    }

    private BasicDataSource dataSource;

    public ConnectionFactory() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(property.getProperty("db.driver"));
        dataSource.setUrl(property.getProperty("db.url"));
        dataSource.setUsername(property.getProperty("db.username"));
        dataSource.setPassword(property.getProperty("db.password"));
        LOGGER.log(Level.INFO, "DataSource was successful generated !");
    }


    public Connection createConnect() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeQuietly(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error: Connection wasn't close!", e);
        }
    }
}
