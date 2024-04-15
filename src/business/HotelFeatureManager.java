package business;

import core.Helper;
import dao.HotelFeatureDao;
import dao.UserDao;
import entity.HotelFeature;
import entity.User;

import java.util.ArrayList;

public class HotelFeatureManager {
    private final HotelFeatureDao hotelFeatureDao;

    public HotelFeatureManager() {
        this.hotelFeatureDao = new HotelFeatureDao();
    }

    public HotelFeature getById(int id) {
        return this.hotelFeatureDao.getById(id);
    }

    public ArrayList<HotelFeature> findAll() {
        return this.hotelFeatureDao.findAll();
    }

    public boolean save(HotelFeature hotelFeature) {

        if (this.getById(hotelFeature.getHotelFeatureId()) != null) {
            Helper.showMessage("error");
            return false;
        }

        return this.hotelFeatureDao.save(hotelFeature);
    }

//    public ArrayList<Object[]> getForTable(int size, ArrayList<User> users) {
//        ArrayList<Object[]> userList = new ArrayList<>();
//        for (User user : users) {
//            int i = 0;
//            Object[] rowObject = new Object[size];
//            rowObject[i++] = user.getId();
//            rowObject[i++] = user.getRole();
//            rowObject[i++] = user.getName();
//            rowObject[i++] = user.getPassword();
//            userList.add(rowObject);
//        }
//        return userList;
//    }

//    public boolean update(Car car) {
//        if (this.getById(car.getId()) == null) {
//            Helper.showMsg("notFound");
//            return false;
//        }
//        return this.carDao.update(car);
//    }
//
//    public boolean delete(int id) {
//        if (this.getById(id) == null) {
//            Helper.showMsg(id + " ID kayıtlı model bulunamadı");
//            return false;
//        }
//        return this.carDao.delete(id);
//    }


}
