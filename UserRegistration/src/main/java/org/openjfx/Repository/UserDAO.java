package org.openjfx.Repository;

import org.openjfx.Configuration.ConnectionFactory;
import org.openjfx.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private ConnectionFactory connectionFactory;

    public UserDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void insert(User user) throws SQLException {
        Connection connection = connectionFactory.createConnect();
        String sql = "INSERT INTO user_table (user_name, password, auto_log_out) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getAutoLogOut());
        boolean result = preparedStatement.executeUpdate() > 0;
        if (result) {
            LOGGER.log(Level.INFO, "User was added to DB");
        }
        connectionFactory.closeQuietly(connection);
        preparedStatement.close();
    }

    public User getUser(String password) throws SQLException {
        Connection connection = connectionFactory.createConnect();
        User user = null;
        String sql = "SELECT * FROM user_table WHERE password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, password);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String username = resultSet.getString("user_name");
            int autoLogOut = resultSet.getInt("auto_log_out");
            user = new User(username, password, autoLogOut);
        }
        if (user != null) {
            LOGGER.log(Level.INFO, "User " + user.getUserName() + " was successful found");
        }
        connectionFactory.closeQuietly(connection);
        resultSet.close();
        statement.close();

        return user;
    }
}

