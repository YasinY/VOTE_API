package com.yasin.voteapi.database;


import com.google.gson.Gson;
import com.yasin.voteapi.datasets.user.User;
import com.yasin.voteapi.status.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Yasin
 */
public class Database {

    private static Database database;

    private SingleConnectionDataSource connection;

    private static Gson gson;


    private Database() {
        connection = initializeConnection();
        gson = new Gson();
    }

    private SingleConnectionDataSource initializeConnection() {
        try {
            return new SingleConnectionDataSource(DriverManager.getConnection("jdbc:postgresql://localhost:5432/votes", "postgres", "test"), true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JdbcTemplate queryOnDemand() {
        return new JdbcTemplate(connection);
    }


    public void updateUser(int voteCount, String username, int pendingPoints, long lastVote, int votesToday) {
        if (getUser(username).hasBody()) {
            queryOnDemand().execute("SELECT update(" + voteCount + "::smallint, '" + username + "'::text, " + pendingPoints + ", " + lastVote + "::bigint, " + votesToday + ")");
        }
    }

    public ResponseEntity getUser(String name) {
        User user = gson.fromJson(queryOnDemand().queryForObject("SELECT get_user('" + name + "', true);", String.class), User.class);
        if (user != null) {
            if (user.getName().isEmpty()) {
                return Status.ERROR_USERNAME_INVALID.build();
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return Status.ERROR_NO_USER_FOUND.build();
    }

    public static Database getInstance() {
        return database == null ? database = new Database() : database;
    }
}
