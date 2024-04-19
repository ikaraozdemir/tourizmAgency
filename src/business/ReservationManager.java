package business;

import dao.ReservationDao;
import entity.Reservation;
import entity.Room;
import entity.RoomFeature;

import java.util.ArrayList;
import java.util.Map;

public class ReservationManager {
    private ReservationDao reservationDao;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservList) {
        ArrayList<Object[]> reservObjList = new ArrayList<>();
        for (Reservation reservation :reservList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = reservation.getReservId();
            rowObject[i++] = reservation.getHotel().getHotelName();
            rowObject[i++] = reservation.getRoom().getType();
            rowObject[i++] = reservation.getCheckinDate();
            rowObject[i++] = reservation.getCheckOutDate();
            rowObject[i++] = reservation.getGuestIdno();
            rowObject[i++] = reservation.getGuestName();
            rowObject[i++] = reservation.getGuestMpno();
            rowObject[i++] = reservation.getGuestMail();
            rowObject[i++] = reservation.getAdultCount();
            rowObject[i++] = reservation.getChildCount();
            rowObject[i++] = reservation.getTotalPrice();
//            rowObject[i++] =
//            rowObject[i++] =
//            rowObject[i++] =
//            ArrayList<String> roomFeatureList = new ArrayList<>();
//            for (RoomFeature feature : room.getRoomFeatures()) {
//                for (Map.Entry<String, Object> entry : feature.getRoomFeature().entrySet()) {
//                    String key = entry.getKey();
//                    Object value = entry.getValue();
//                    String keyValueString = key + ": " + value.toString();
//                    roomFeatureList.add(keyValueString);
//                }
//            }
//            rowObject[i++] = roomFeatureList;
            reservObjList.add(rowObject);
        }
        return reservObjList;
    }
}
