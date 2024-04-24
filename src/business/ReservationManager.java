package business;

import core.Helper;
import dao.ReservationDao;
import entity.*;

import java.time.format.DateTimeFormatter;
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
        // Oda stoğunu güncelleme
        int roomId = reservation.getReservRoomId();
        if (this.roomManager.updateRoomStock(roomId, -1) && this.reservationDao.save(reservation)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Reservation reservation) {
        if (this.getById(reservation.getReservId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.reservationDao.update(reservation);
    }

    public boolean delete(int id) {
        Reservation reservation = this.getById(id);
        if (reservation == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        int roomId = reservation.getReservRoomId();

        return this.reservationDao.delete(id) && this.roomManager.updateRoomStock(roomId, 1);
    }

    public boolean deleteByRoomId(int roomId) {
        Reservation reservation = this.getByRoomId(roomId);
        if (reservation == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.reservationDao.deleteByRoomId(roomId);
    }

    public boolean deleteByHotelId(int hotelId) {
        Reservation reservation = this.getByHotelId(hotelId);
        if (reservation == null) {
            Helper.showMessage("kayıt bulunamadı");
            return false;
        }
        return this.reservationDao.deleteByHotelId(hotelId);
    }

    public Reservation getByRoomId(int roomId) {
        return this.reservationDao.getByRoomId(roomId);
    }

    public Reservation getByHotelId(int hotelId) {
        return this.reservationDao.getByHotelId(hotelId);
    }

    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservList) {

        ArrayList<Object[]> reservObjList = new ArrayList<>();

        for (Reservation reservation : reservList) {

            Hotel hotel = this.hotelManager.getById(reservation.getReservHotelId());
            ArrayList<Room> rooms = this.roomManager.getRoomsWithDetails(reservation.getReservRoomId());

            StringBuilder hotelFeatures = new StringBuilder();
            String roomType = "";

            for (HotelFeature hotelFeature : hotel.getHotelFeatures()) {
                hotelFeatures.append(hotelFeature).append(", ");
            }
            for (Room room : rooms) {
                roomType = room.getType().toString();
            }

            int i = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Object[] rowObject = new Object[size];
            rowObject[i++] = reservation.getReservId();
            rowObject[i++] = hotel.getHotelName();
            rowObject[i++] = roomType;
            rowObject[i++] = reservation.getCheckinDate().format(formatter);
            rowObject[i++] = reservation.getCheckOutDate().format(formatter);
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
