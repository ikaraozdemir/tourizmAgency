package business;
import core.Helper;
import dao.PensionDao;
import entity.HotelFeature;
import entity.Pension;
import java.util.ArrayList;

public class PensionManager {
    private final PensionDao pensionDao;

    public PensionManager() {
        this.pensionDao = new PensionDao();
    }

    public Pension getById(int id) {
        return this.pensionDao.getById(id);
    }

    public ArrayList<Pension> findAll() {
        return this.pensionDao.findAll();
    }

    public boolean save(Pension pension) {

            if (this.getById(pension.getPensionId()) != null) {
                Helper.showMessage("error");
                return false;
            }

        return this.pensionDao.save(pension);
    }
    public ArrayList<Pension> getPensionsByHotelId(int id){
        return this.pensionDao.getPensionsByHotelId(id);
    }

    public boolean update(Pension pension) {
        if (this.getById(pension.getPensionId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.pensionDao.update(pension);
    }

    public boolean delete(int hotelId) {
        if (this.getPensionsByHotelId(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.pensionDao.delete(hotelId);
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
