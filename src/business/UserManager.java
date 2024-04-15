package business;

import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username, password);
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }


    public ArrayList<Object[]> getForTable(int size, ArrayList<User> users) {
        ArrayList<Object[]> userList = new ArrayList<>();
        for (User user : users) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = user.getId();
            rowObject[i++] = user.getRole();
            rowObject[i++] = user.getName();
            rowObject[i++] = user.getPassword();
            userList.add(rowObject);
        }
        return userList;
    }

}
