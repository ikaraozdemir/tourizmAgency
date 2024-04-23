package business;

import core.Helper;
import dao.ReservationDao;
import dao.RoomDao;
import dao.RoomFeatureDao;
import entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RoomManager {
    private RoomDao roomDao;
    private ReservationDao reservationDao;
    private RoomFeatureDao roomFeatureDao;
    private String checkOutDate;
    private String checkInDate;
    private int adult;
    private int child;
    private int totalPrice;
    private int totalDays;
    private HotelManager hotelManager;

    public RoomManager() {
        this.roomDao = new RoomDao();
        this.reservationDao = new ReservationDao();
        this.roomFeatureDao = new RoomFeatureDao();
        this.hotelManager = new HotelManager();
    }

    public Room getById(int id) {
        return this.roomDao.getById(id);
    }

    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    public ArrayList<Room> getRoomsWithDetails(Integer id) {
        return this.roomDao.getRoomsWithDetails(id);
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

            StringBuilder roomFeature = new StringBuilder();
            for (RoomFeature feature : room.getRoomFeatures()) {
                for (Map.Entry<String, Object> entry : feature.getRoomFeature().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    String keyValue;
                    if (key.endsWith(":")) {
                        keyValue = key + " " + value.toString();
                    } else {
                        keyValue = key + ": " + value.toString();
                    }
                    roomFeature.append(keyValue).append(", "); // Her özelliği ekle
                }
            }
            String roomFeatures = roomFeature.toString();
            if (roomFeatures.length() > 2) {
                roomFeatures = roomFeatures.substring(0, roomFeatures.length() - 2);
            }
            rowObject[i++] = roomFeatures;
            roomObjList.add(rowObject);
        }
        return roomObjList;
    }


    public ArrayList<Object[]> getForSearchedRoomTable(int size, ArrayList<Room> roomList) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateIn = LocalDate.parse(checkInDate, formatter);
        LocalDate dateOut = LocalDate.parse(checkOutDate, formatter);
        long totalDays = ChronoUnit.DAYS.between(dateIn, dateOut);
        this.totalDays = (int) totalDays;

        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room room :roomList) {
            int i = 0;
            int totalChildAdultPrice = adult * room.getPriceAdult() + child * room.getPriceChild();
            int totalPrice = (int) (totalChildAdultPrice * totalDays);
            this.totalPrice = totalPrice;
            Object[] rowObject = new Object[size];
            rowObject[i++] = room.getRoomId();
            rowObject[i++] = room.getHotel().getHotelName();
            rowObject[i++] = room.getHotel().getHotelId();
            rowObject[i++] = room.getSeason().getStrtDate();
            rowObject[i++] = room.getSeason().getEndDate();
            rowObject[i++] = room.getPension().getPensionType();
            rowObject[i++] = room.getRoomStock();
            rowObject[i++] = room.getType();

            StringBuilder roomFeature = new StringBuilder();
            for (RoomFeature feature : room.getRoomFeatures()) {
                for (Map.Entry<String, Object> entry : feature.getRoomFeature().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    String keyValue;
                    if (key.endsWith(":")) {
                        keyValue = key + " " + value.toString();
                    } else {
                        keyValue = key + ": " + value.toString();
                    }
                    roomFeature.append(keyValue).append(", "); // Her özelliği ekle
                }
            }
            String roomFeatures = roomFeature.toString();
            if (roomFeatures.length() > 2) {
                roomFeatures = roomFeatures.substring(0, roomFeatures.length() - 2);
            }
            rowObject[i++] = roomFeatures;
            rowObject[i++] = totalDays;
            rowObject[i++] = room.getPriceAdult();
            rowObject[i++] = room.getPriceChild();
            rowObject[i++] = totalPrice;

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

    public boolean updateRoomStock(int roomId, int change) {
        if(this.getById(roomId) == null){
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.roomDao.updateRoomStock(roomId, change);
    }

    public boolean delete(int roomId) {
        if (this.getById(roomId) == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.roomDao.delete(roomId);
    }
    public ArrayList<Room> searchForReservation(String checkInDate, String checkOutDate, String hotelName, String hotelCity, int adult, int child ) {

        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adult = adult;
        this.child = child;

        String query = "SELECT * FROM room r INNER JOIN season s  ON r.room_season_id = s.season_id INNER JOIN hotel h ON r.room_hotel_id = h.hotel_id ";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> rezervOrWhere = new ArrayList<>();

        int total_guest = adult + child;

        checkInDate = LocalDate.parse(checkInDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        checkOutDate = LocalDate.parse(checkOutDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        if (checkInDate != null) where.add("s.season_start <= '" + checkInDate + "'");
        if (checkOutDate != null) where.add("s.season_end >= '" + checkOutDate + "'");
        if (!Objects.equals(hotelCity, "")) where.add("h.hotel_city = '" + hotelCity + "'");
        if (!Objects.equals(hotelName, "")) where.add("h.hotel_name = '" + hotelName + "'");

        String whereStr = String.join(" AND ", where);

        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        ArrayList<Room> searchedRoomList = this.roomDao.selectByQuery(query);
        ArrayList<Room> searchedRoomListUpdated = new ArrayList<>();

        for(Room room: searchedRoomList) {
           ArrayList<Room> roomsWithDetails0 = this.getRoomsWithDetails(room.getRoomId());
           searchedRoomListUpdated.add (roomsWithDetails0.get(0));
        }

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
        searchedRoomListUpdated.removeIf(room -> reservedRoomId.contains(room.getRoomId()));


        List<Room> roomsToRemove = new ArrayList<>();

        for (Room room : searchedRoomListUpdated) {
            for (RoomFeature roomFeature : room.getRoomFeatures()) {
                if (Objects.equals(roomFeature.getRoomFeature().keySet().toString(), "[[Yatak Sayısı:]]")) {
                    String str1 = roomFeature.getRoomFeature().values().toString();
                    String str2 = str1.substring(2, str1.length() - 2);
                    int bedCount = Integer.parseInt(str2);
                    if (bedCount < total_guest && room.getRoomStock() < 1) {
                        System.out.println(bedCount + " yatak sayısı, " + total_guest + " total misafir");
                        roomsToRemove.add(room);
                    }
                }
            }
        }

// Silinecek odaların referanslarını listeden kaldırın
        searchedRoomListUpdated.removeAll(roomsToRemove);

        for (Room room: searchedRoomListUpdated) {
            System.out.println(room.getHotel().getHotelFeatures() + " searchedRoomList roomManager");

        }

        return searchedRoomListUpdated;
    }

}
