package business;

import core.Helper;
import dao.PensionDao;
import dao.ReservationDao;
import dao.RoomDao;
import dao.RoomFeatureDao;
import entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private RoomDao roomDao;
    private ReservationDao reservationDao;
    private RoomFeatureDao roomFeatureDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
        this.reservationDao = new ReservationDao();
        this.roomFeatureDao = new RoomFeatureDao();
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

    public boolean update(Room room) {

        if (this.getById(room.getRoomId()) == null) {
            Helper.showMessage("error");
            return false;
        }
        return this.roomDao.update(room);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomList) {
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room room :roomList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = room.getRoomId();
            rowObject[i++] = room.getHotel().getHotelName();
            rowObject[i++] = room.getHotel().getHotelId();
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

    public boolean delete(int roomId) {
        if (this.getById(roomId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.roomDao.delete(roomId);
    }
    public ArrayList<Room> searchForReservation(String checkInDate, String checkOutDate, String hotelName, String hotelCity, int adult, int child ) {
        String query = "SELECT FROM room r INNER JOIN season s  ON r.room_season_id = s.season_id INNER JOIN hotel h ON r.room_hotel_id = h.hotel_id;";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> rezervOrWhere = new ArrayList<>();

        int total_guest = adult + child;



//        checkIn = LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
//        checkOut = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        if (checkInDate != null) where.add("s.season_start <= '" + checkInDate + "'");
        if (checkOutDate != null) where.add("s.season_end >= '" + checkInDate + "'");
        if (hotelCity != null) where.add("h.hotel_city = '" + hotelCity + "'");
        if (hotelName != null) where.add("h.hotel_name = '" + hotelName + "'");
        if (hotelName != null) where.add("h.hotel_name = '" + hotelName + "'");

        String whereStr = String.join(" AND ", where);

        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        ArrayList<Room> searchedRoomList = this.roomDao.selectByQuery(query);

        rezervOrWhere.add("('" + checkInDate + "' BETWEEN checkin_date AND checkout_date)");
        rezervOrWhere.add("('" + checkOutDate + "' BETWEEN checkin_date AND checkout_date)");
        rezervOrWhere.add("(checkin_date BETWEEN '" + checkInDate + "' AND '" + checkOutDate + "')");
        rezervOrWhere.add("(checkout_date BETWEEN '" + checkInDate + "' AND '" + checkOutDate + "')");

        String rezervOrWhereStr = String.join(" OR ", rezervOrWhere);
        String reservQuery = "SELECT * FROM public.reservation WHERE " + rezervOrWhereStr;

        ArrayList<Reservation> reservList = this.reservationDao.selectByQuery(reservQuery);
        ArrayList<Integer> reservedRoomId = new ArrayList<>();

        for (Reservation reservation : reservList) {
            reservedRoomId.add(reservation.getReservRoomId());
        }
        searchedRoomList.removeIf(room -> reservedRoomId.contains(room.getRoomId()));

        String roomFeaturequery = "SELECT rf.feature_name, rf.feature_value, rf.room_feature_room_id FROM public.room_features rf";
        ArrayList<RoomFeature> roomFeatureList = this.roomFeatureDao.selectByQuery(roomFeaturequery);
//        ArrayList<Integer> reservedRoomFeatureId = new ArrayList<>();

        for (RoomFeature roomFeature : roomFeatureList) {
           Object bedCount = roomFeature.getRoomFeature().get("[Oda Boyutu (metrekare):]");
           if ((int)bedCount < total_guest) {
               int roomIdBedNotEnough = roomFeature.getRoomFeatureRoomId();
               searchedRoomList.removeIf(room -> reservedRoomId.contains(roomIdBedNotEnough));
            }
        }
        searchedRoomList.removeIf(room -> reservedRoomId.contains(room.getRoomId()));


        return searchedRoomList;
    }




}
