package business;

import core.Helper;
import dao.UserDao;
import entity.Season;
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

    public User getById(int id) {
        return this.userDao.getById(id);
    }

    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.userDao.update(user);
    }

    public boolean save(User user) {

        if (this.getById(user.getId()) != null) {
            Helper.showMessage("error");
            return false;
        }

        return this.userDao.save(user);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.userDao.delete(id);
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
