package dao;

import business.HotelManager;
import business.RoomManager;
import core.Database;
import entity.Hotel;
import entity.Reservation;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDao {
    private Connection connection;

    public ReservationDao() {
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

    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation " +
                "(" +
                "reserv_room_id," +
                "adult_guest_count," +
                "child_guest_count," +
                "reserv_guest_idno," +
                "reserv_guest_name," +
                "reserv_guest_mpno," +
                "reserv_guest_mail," +
                "reserv_total_prc," +
                "reserv_note," +
                "checkin_date," +
                "checkout_date," +
                "reserv_hotel_id" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, reservation.getReservRoomId());
            pr.setInt(2, reservation.getAdultCount());
            pr.setInt(3, reservation.getChildCount());
            pr.setString(4, reservation.getGuestIdno());
            pr.setString(5, reservation.getGuestName());
            pr.setString(6, reservation.getGuestMpno());
            pr.setString(7, reservation.getGuestMail());
            pr.setInt(8, reservation.getTotalPrice());
            pr.setString(9, reservation.getReservNote());
            pr.setDate(10, Date.valueOf(reservation.getCheckinDate()));
            pr.setDate(11, Date.valueOf(reservation.getCheckOutDate()));
            pr.setInt(12, reservation.getHotel().getHotelId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET " +
                "reserv_room_id = ? ," +
                "adult_guest_count = ? ," +
                "child_guest_count = ? ," +
                "reserv_guest_idno = ? ," +
                "reserv_guest_name = ? ," +
                "reserv_guest_mpno = ? ," +
                "reserv_guest_mail = ? ," +
                "reserv_total_prc = ? ," +
                "reserv_note = ? ," +
                "checkin_date = ? ," +
                "checkout_date = ? ," +
                "reserv_hotel_id = ? " +
                "WHERE reserv_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, reservation.getReservRoomId());
            pr.setInt(2, reservation.getAdultCount());
            pr.setInt(3, reservation.getChildCount());
            pr.setString(4, reservation.getGuestIdno());
            pr.setString(5, reservation.getGuestName());
            pr.setString(6, reservation.getGuestMpno());
            pr.setString(7, reservation.getGuestMail());
            pr.setInt(8, reservation.getTotalPrice());
            pr.setString(9, reservation.getReservNote());
            pr.setDate(10, Date.valueOf(reservation.getCheckinDate()));
            pr.setDate(11, Date.valueOf(reservation.getCheckOutDate()));
            pr.setInt(12, reservation.getHotel().getHotelId());
            pr.setInt(13, reservation.getReservId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.reservation WHERE reserv_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservId(rs.getInt("reserv_id"));
        reservation.setReservRoomId(rs.getInt("reserv_room_id"));
        reservation.setAdultCount(rs.getInt("adult_guest_count"));
        reservation.setChildCount(rs.getInt("child_guest_count"));
        reservation.setGuestIdno(rs.getString("reserv_guest_idno"));
        reservation.setGuestMpno(rs.getString("reserv_guest_mpno"));
        reservation.setGuestName(rs.getString("reserv_guest_name"));
        reservation.setGuestMail(rs.getString("reserv_guest_mail"));
        reservation.setTotalPrice(rs.getInt("reserv_total_prc"));
        reservation.setReservNote(rs.getString("reserv_note"));
        reservation.setTotalDayCount(rs.getInt("reserv_total_days"));
        reservation.setCheckinDate(rs.getDate("checkin_date").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("checkout_date").toLocalDate());
        reservation.setReservHotelId(rs.getInt("reserv_hotel_id"));
//        reservation.setRoom(this.roomManager.getById(rs.getInt("reserv_room_id")));
//        reservation.setHotel(this.hotelManager.getById(rs.getInt("reserv_hotel_id")));
        return reservation;
    }

}
