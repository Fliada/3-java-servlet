package example.dbService.dao;

import example.accounts.UserProfile;
import example.dbService.dataSets.UsersDataSet;
import example.dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getString(4));
        });
    }

    public UsersDataSet getUserByLogin(String name) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + name + "'", result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getString(4));
        });
    }

    public long getUserId(UserProfile user) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + user.getLogin() + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public void insertUser(UserProfile user) throws SQLException {
        executor.execUpdate("insert into users (user_name, user_email, user_pass) values ('" + user.getLogin() + "', '" + user.getEmail() + "', '" + user.getPass() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, user_name varchar(256), user_email varchar(256), user_pass varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
