package business;

import core.Helper;
import dao.PensionDao;
import dao.RoomDao;
import entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
    }

    public Room getById(int id) {
        return this.roomDao.getById(id);
    }

    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    public ArrayList<Room> getRoomsWithDetails() {
        return this.roomDao.getRoomsWithDetails();
    }

    public boolean save(Room room) {

        if (this.getById(room.getRoomId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.roomDao.save(room);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomList) {
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room room :roomList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = room.getRoomId();
            rowObject[i++] = room.getHotel().getHotelName();
            rowObject[i++] = room.getSeason().getStrtDate();
            rowObject[i++] = room.getSeason().getEndDate();
            rowObject[i++] = room.getPension().getPensionType();
            rowObject[i++] = room.getRoomStock();
            rowObject[i++] = room.getPriceAdult();
            rowObject[i++] = room.getPriceChild();
            rowObject[i++] = room.getType();
            ArrayList<String> roomFeatureList = new ArrayList<>();
            for (RoomFeature feature : room.getRoomFeatures()) {
                for (Map.Entry<String, Object> entry : feature.getRoomFeature().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    String keyValueString = key + ": " + value.toString();
                    roomFeatureList.add(keyValueString);
                }
            }


            rowObject[i++] = roomFeatureList;
            roomObjList.add(rowObject);
        }
        return roomObjList;
    }

    public int saveForId(Room room){
        return this.roomDao.saveForId(room);
    }

    public int saveAndGetRoomId(Room room) {
        int roomId = 0;
        try {
            roomId = saveForId(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomId;
    }

//    public boolean delete(int roomId) {
//        if (this.getRoomsByHotelId(hotelId) == null) {
//            Helper.showMessage("kayıt bulunamadı");
//            return false;
//        }
//        return this.roomDao.delete(roomId);
//    }


}
