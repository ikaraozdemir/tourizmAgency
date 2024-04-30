package business;

import core.Helper;
import dao.SeasonDao;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.time.format.DateTimeFormatter;
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

    public ArrayList<Season> getSeasonsByHotelId(int id) {
        return this.seasonDao.getSeasonsByHotelId(id);
    }

    public boolean delete(int seasonId) {
        if (this.getSeasonsByHotelId(seasonId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.seasonDao.delete(seasonId);
    }

    public boolean deleteByHotelId(int hotelId) {
        if (this.getSeasonsByHotelId(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.seasonDao.deleteByHotelId(hotelId);
    }

    public boolean update2(ArrayList<Season> selectedSeasons, int hotelId, ArrayList<Integer> seasonIds) {
        if (hotelId == 0) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.seasonDao.update2(selectedSeasons, hotelId, seasonIds);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Season> seasons) {
        ArrayList<Object[]> seasonList = new ArrayList<>();
        for (Season season : seasons) {
            int i = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Object[] rowObject = new Object[size];
            rowObject[i++] = season.getSeasonId();
            rowObject[i++] = this.hotelManager.getById(season.getSeasonHotelId()).getHotelName();
            rowObject[i++] = season.getStrtDate().format(formatter);
            rowObject[i++] = season.getEndDate().format(formatter);
            rowObject[i++] = season.getSeasonName();
            seasonList.add(rowObject);
        }
        return seasonList;
    }
}
