package business;

import core.Helper;
import dao.SeasonDao;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.util.ArrayList;

public class SeasonManager {
    private final SeasonDao seasonDao;

    public SeasonManager() {
        this.seasonDao = new SeasonDao();
    }

    public Season getById(int id) {
        return this.seasonDao.getById(id);
    }

    public ArrayList<Season> findAll() {
        return this.seasonDao.findAll();
    }

    public boolean save(Season season) {

            if (this.getById(season.getSeasonId()) != null) {
                Helper.showMessage("error");
                return false;
            }

        return this.seasonDao.save(season);
    }

    public ArrayList<Season> getSeasonsByHotelId(int id){
        return this.seasonDao.getSeasonsByHotelId(id);
    }

//    public boolean update(Season season) {
//        if (this.getById(season.getSeasonId()) == null) {
//            Helper.showMessage("notFound");
//            return false;
//        }
//        return this.seasonDao.update(season);
//    }

    public boolean delete(int hotelId) {
        if (this.getSeasonsByHotelId(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.seasonDao.delete(hotelId);
    }

}
