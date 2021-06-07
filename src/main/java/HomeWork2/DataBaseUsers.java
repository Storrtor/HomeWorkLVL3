package HomeWork2;

import java.sql.*;

public class DataBaseUsers implements AuthService {
    private static Connection connection;
    private static Statement stmt;

//main тоже не использую в чатике, но для управления бд он мне нужен
    public static void main(String[] args) {
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
        connection = DriverManager.getConnection(ChatConstants.DATABASE_URL);
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

// Никак не использую в чатике, но оставлю, пусть будет, для управления бд это мне нужно
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
//
//    private void clearTable() throws SQLException {
//        stmt.executeUpdate("DELETE FROM users;");
//    }
//
//
//    private void delete(String nick) throws SQLException {
//        stmt.executeUpdate("DELETE FROM users WHERE nick = '" + nick + "';");
//    }

    public void updateNick(String newName, String oldName){
        String str = "UPDATE users SET nick = '" + newName + "' WHERE nick = '" + oldName + "'";
        try {
            stmt.execute(str);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public boolean isNickBusy(String nick) {
        try (ResultSet rs = stmt.executeQuery("SELECT nick FROM users WHERE nick = '" + nick + "'")){
            while (rs.next()){
                System.out.println(rs.getString(1));
                return false;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return true;
    }

    private void insert(String nick, String login, String pass) throws SQLException {
        stmt.executeUpdate(
                "INSERT INTO users (nick, login, pass) VALUES " +
                        "('" + nick + "', '" + login + "', '" + pass + "');");
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
