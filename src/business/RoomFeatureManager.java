package business;

import core.Helper;
import dao.HotelFeatureDao;
import dao.RoomFeatureDao;
import entity.HotelFeature;
import entity.Pension;
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
    public ArrayList<RoomFeature> getFeaturesByRoomId(int id){
        return this.roomFeatureDao.getFeaturesByRoomId(id);
    }

    public boolean delete(int roomId) {
        if (this.getFeaturesByRoomId(roomId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.roomFeatureDao.delete(roomId);
    }

    public boolean update2(ArrayList<RoomFeature> selectedRoomFeature, int roomId) {
        if (roomId == 0) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.roomFeatureDao.update2(selectedRoomFeature, roomId);
    }

}
