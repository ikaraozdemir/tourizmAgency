package business;

import core.Helper;
import dao.HotelFeatureDao;
import dao.RoomFeatureDao;
import entity.HotelFeature;
import entity.RoomFeature;

import java.util.ArrayList;

public class RoomFeatureManager {
    private final RoomFeatureDao roomFeatureDao;

    public RoomFeatureManager() {
        this.roomFeatureDao = new RoomFeatureDao();
    }

    public RoomFeature getById(int id) {
        return this.roomFeatureDao.getById(id);
    }

    public ArrayList<RoomFeature> findAll() {
        return this.roomFeatureDao.findAll();
    }

    public boolean save(RoomFeature roomFeature) {
        if (this.getById(roomFeature.getRoomFeatureId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.roomFeatureDao.save(roomFeature);
    }

//    public boolean delete(int roomId) {
//        if (this.getFeaturesByRoomId(roomId) == null) {
//            Helper.showMessage("kayıt bulunamadı");
//            return false;
//        }
//        return this.roomFeatureDao.delete(roomId);
//    }


}
