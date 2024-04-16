package business;

import core.Helper;
import dao.HotelDao;
import entity.Hotel;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotelList) {
        ArrayList<Object[]> hotelObjList = new ArrayList<>();
        for (Hotel hotel :hotelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = hotel.getHotelId();
            rowObject[i++] = hotel.getCity();
            rowObject[i++] = hotel.getRegion();
            rowObject[i++] = hotel.getHotelName();
            rowObject[i++] = hotel.getHotelPhno();
            rowObject[i++] = hotel.getHotelMail();
            rowObject[i++] = hotel.getStar();
            rowObject[i++] = hotel.getHotelAddress();

            ArrayList<String> pensionTypeList = new ArrayList<>();
            for (Pension pension : hotel.getPensionTypes()) {
                pensionTypeList.add(pension.getPensionType());
            }
            rowObject[i++] = pensionTypeList;

            ArrayList<String> hotelFeatureList = new ArrayList<>();
            for (HotelFeature hotelFeature : hotel.getHotelFeatures()) {
                hotelFeatureList.add(hotelFeature.getHotelFeature());
            }
            rowObject[i++] = hotelFeatureList;
            hotelObjList.add(rowObject);

            ArrayList<String> seasonNameList = new ArrayList<>();
            for (Season season : hotel.getSeasons()) {
                seasonNameList.add(season.getSeasonName());
            }
            rowObject[i++] = seasonNameList;


        }
        return hotelObjList;
    }

    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public boolean save(Hotel hotel) {
        if (this.getById(hotel.getHotelId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }

    public int saveForId(Hotel hotel){
        return this.hotelDao.saveForId(hotel);
    }

    public int saveAndGetHotelId(Hotel hotel) {
        int hotelId = 0;
        try {
            hotelId = saveForId(hotel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotelId;
    }

    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }



    public ArrayList<Hotel> findHotelsWithFeatures() {
        return this.hotelDao.findHotelsWithFeatures();
    }

    public boolean update(Hotel hotel) {
        if (this.getById(hotel.getHotelId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.hotelDao.update(hotel);
    }

    public boolean delete(int hotelId) {
        if (this.getById(hotelId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.hotelDao.delete(hotelId);
    }



//    public ArrayList<Hotel> searchForTable(int brandId, Model.Fuel fuel, Model.Gear gear, Model.Type type) {
//
//        String select = "SELECT * FROM  public.model";
//        ArrayList<String> whereList = new ArrayList<>();
//
//        if (brandId != 0) {
//            whereList.add("model_brand_id =" + brandId);
//        }
//        if (fuel != null) {
//            whereList.add("model_fuel ='" + fuel.toString() + "'");
//        }
//        if (gear != null) {
//            whereList.add("model_gear = '" + gear.toString() + "'");
//        }
//        if (type != null) {
//            whereList.add("model_type = '" + fuel.toString() + "'");
//        }
//
//        String whereStr = String.join(" AND ", whereList);
//        String query = select;
//        if (whereStr.length() > 0) {
//            query += " WHERE " + whereStr;
//        }
//        return this.modelDao.selectByQuery(query);
//    }
}