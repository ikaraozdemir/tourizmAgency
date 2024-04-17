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

//    public boolean update(HotelFeature hotelFeature) {
//        if (this.getFeaturesByHotelId(hotelFeature.getHotelFeatureHotelId()) == null) {
//            System.out.println(hotelFeature.getHotelFeatureId());
//            Helper.showMessage("notFound");
//            return false;
//        }
//        return this.hotelFeatureDao.update(hotelFeature);
//    }

    public ArrayList<HotelFeature> getFeaturesByHotelId(int id){
        return this.hotelFeatureDao.getFeaturesByHotelId(id);
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
    public boolean delete(int hotelId) {
        if (this.getFeaturesByHotelId(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.hotelFeatureDao.delete(hotelId);
    }


}
