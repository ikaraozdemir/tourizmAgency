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

    public ArrayList<Pension> getPensionsByHotelId(int id) {
        return this.pensionDao.getPensionsByHotelId(id);
    }


    public boolean delete(int hotelId) {
        if (this.getPensionsByHotelId(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.pensionDao.delete(hotelId);
    }

    public boolean update2(ArrayList<Pension> selectedPensions, int hotelId, ArrayList<Integer> pensionIds) {
        if (hotelId == 0) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.pensionDao.update2(selectedPensions, hotelId, pensionIds);
    }

}
