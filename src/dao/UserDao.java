package dao;

import core.Database;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = Database.getInstance();
    }

    public User match(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setRole(rs.getString("user_role"));
        user.setName(rs.getString("user_name"));
        user.setPassword(rs.getString("user_pw"));
        return user;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.user";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public User findByLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_pw = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                user = this.match(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

}
