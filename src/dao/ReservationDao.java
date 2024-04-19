package dao;

import business.HotelManager;
import business.RoomManager;
import core.Database;
import entity.Reservation;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;
    private RoomManager roomManager;
    private HotelManager hotelManager;

    public ReservationDao() {
        this.roomManager = new RoomManager();
        this.hotelManager = new HotelManager();
        this.connection = Database.getInstance();
    }

    public Reservation getById(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE reserv_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Reservation> findAll() {
        return this.selectByQuery("SELECT * FROM public.reservation ORDER BY reserv_id ASC");
    }

    public ArrayList<Reservation> selectByQuery(String query) {
        ArrayList<Reservation> rooms = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                rooms.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservId(rs.getInt("reserv_id"));
        reservation.setReservRoomId(rs.getInt("reserv_room_id"));
        reservation.setAdultCount(rs.getInt("adult_guest_count"));
        reservation.setChildCount(rs.getInt("child_guest_count"));
        reservation.setGuestIdno(rs.getString("reserv_guest_idno"));
        reservation.setGuestMpno(rs.getString("reserv_guest_mpno"));
        reservation.setGuestMail(rs.getString("reserv_guest_mail"));
        reservation.setTotalPrice(rs.getInt("reserv_total_prc"));
        reservation.setReservNote(rs.getString("reserv_note"));
        reservation.setTotalDayCount(rs.getInt("reserv_total_days"));
        reservation.setTotalGuestCount(rs.getInt("reserv_total_guests"));
        reservation.setCheckinDate(rs.getDate("checkin_date").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
        reservation.setRoom(this.roomManager.getById(rs.getInt("reserv_room_id")));
        reservation.setHotel(this.hotelManager.getById(rs.getInt("reserv_hotel_id")));
        return reservation;
    }

}
