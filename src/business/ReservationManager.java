package business;

import core.Helper;
import dao.ReservationDao;
import entity.*;

import java.util.ArrayList;
import java.util.Map;

public class ReservationManager {
    private ReservationDao reservationDao;
    private HotelManager hotelManager;
    private RoomManager roomManager;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
    }

    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }

    public boolean save(Reservation reservation) {
        if (this.getById(reservation.getReservId()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.reservationDao.save(reservation);
    }

    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }


    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservList) {


        ArrayList<Object[]> reservObjList = new ArrayList<>();

        for (Reservation reservation :reservList) {
            Hotel hotel = this.hotelManager.getById(reservation.getReservHotelId());
            ArrayList<Room> rooms = this.roomManager.getRoomsWithDetails(reservation.getReservRoomId());

            String hotelFeatures = "";
            String roomType = "";


            for (HotelFeature hotelFeature : hotel.getHotelFeatures()) {
                hotelFeatures += hotelFeature + ", ";
            }


            for (Room room: rooms ){
               roomType = room.getType().toString();
            }

            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = reservation.getReservId();
            rowObject[i++] = hotel.getHotelName();
            rowObject[i++] = roomType;
//            rowObject[i++] = hotelFeatures;
            rowObject[i++] = reservation.getCheckinDate();
            rowObject[i++] = reservation.getCheckOutDate();
            rowObject[i++] = reservation.getGuestIdno();
            rowObject[i++] = reservation.getGuestName();
            rowObject[i++] = reservation.getGuestMpno();
            rowObject[i++] = reservation.getGuestMail();
            rowObject[i++] = reservation.getAdultCount();
            rowObject[i++] = reservation.getChildCount();
            rowObject[i++] = reservation.getTotalPrice();

            reservObjList.add(rowObject);
        }
        return reservObjList;
    }
}

//        {"ID", "Otel İsmi","Oda Tipi", "Check-in", "Check-out", "Misafir TC No",
//        "Misafir İsim", "Misafir Numara", "Misafir Mail", "Yetişkin Misafir", "Çocuk Misafir", "Total Fiyat"};
