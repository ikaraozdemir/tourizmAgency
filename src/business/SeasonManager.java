package business;

import core.Helper;
import dao.SeasonDao;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.util.ArrayList;

public class SeasonManager {
    private final SeasonDao seasonDao;
    private HotelManager hotelManager = new HotelManager();

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

        public ArrayList<Object[]> getForTable(int size, ArrayList<Season> seasons) {
        ArrayList<Object[]> seasonList = new ArrayList<>();
        for (Season season : seasons) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = season.getSeasonId();
            rowObject[i++] = this.hotelManager.getById(season.getSeasonHotelId()).getHotelName();
            rowObject[i++] = season.getStrtDate();
            rowObject[i++] = season.getEndDate();
            rowObject[i++] = season.getSeasonName();
            seasonList.add(rowObject);
        }
        return seasonList;
    }

}
