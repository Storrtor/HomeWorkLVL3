package HomeWork2;

import java.sql.*;

public class DataBaseUsers implements AuthService {
    private Connection connection;
    private Statement stmt;

    public static void main(String[] args) { //main не нужен

        DataBaseUsers dataBaseUsers = new DataBaseUsers();
        try {

            dataBaseUsers.connect();
            dataBaseUsers.read();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBaseUsers.disconnect();
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:javadb.db");
        stmt = connection.createStatement();
    }

    private void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (\n" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        " nick TEXT, \n" +
                        " login TEXT, \n" +
                        " pass TEXT\n" +
                        ");");
    }

    private void dropTable() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS users;");
    }

    private void read() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM users;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) +
                        " " + rs.getString("nick") +
                        " " + rs.getString("login") +
                        " " + rs.getString("pass")
                );
            }
        }
    }

    private void clearTable() throws SQLException {
        stmt.executeUpdate("DELETE FROM users;");
    }

    private void insert(String nick, String login, String pass) throws SQLException {
        stmt.executeUpdate(
                "INSERT INTO users (nick, login, pass) VALUES " +
                        "('" + nick + "', '" + login + "', '" + pass + "');");
    }

    private void delete(String nick) throws SQLException {
        stmt.executeUpdate("DELETE FROM users WHERE nick = '" + nick + "';");
    }

    private void updateNick(String oldNick, String newNick) throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT nick FROM users")) {
            while (rs.next()) {
                if (newNick.equals(rs.getString(1))) {
                    System.out.println("Такой никнейм уже существует");
                } else {
                    stmt.executeUpdate("UPDATE users SET nick ='" + newNick + "' WHERE nick = '" + oldNick + "';");
                }
            }
        }
    }

    private String takeNick(String login, String pass) throws SQLException { //prepareted
        try (ResultSet rs = stmt.executeQuery("SELECT nick FROM users WHERE login = '" + login + "' AND pass = '" + pass + "'")) {
            return rs.getString(1);
        }
    }


    @Override
    public void start() {
        //включение конект
        try {
            connect();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println(this.getClass().getName() + " server started");
    }

    @Override
    public void stop() {
        //выкл конект
        System.out.println(this.getClass().getName() + " server stopped");
        disconnect();
    }

    @Override
    public String getNickByLoginAndPass(String login, String pass) throws SQLException {
        return takeNick(login, pass);
    }

}
