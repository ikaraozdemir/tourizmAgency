package dao;

import core.Database;
import entity.Pension;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        this.connection = Database.getInstance();
    }

    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) obj = this.match(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> rooms = new ArrayList<>();
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

    public boolean delete(int hotelId) {
        String query = "DELETE FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotelId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(Room room) {
        String query = "INSERT INTO public.room (" +
                "room_hotel_id, " +
                "room_season_id, " +
                "room_pension_id, " +
                "room_stock, " +
                "prc_for_child, " +
                "prc_for_adult, " +
                "room_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, room.getRoomHotelId());
            pr.setInt(2,room.getSeason().getSeasonId());
            pr.setInt(3,room.getPension().getPensionId());
            pr.setInt(4, room.getRoomStock());
            pr.setInt(5, room.getPriceChild());
            pr.setInt(6, room.getPriceAdult());
            pr.setString(7, String.valueOf(room.getType()));
            pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int saveForId(Room room) {
        int generatedId = 0;
        String query = "INSERT INTO public.room (" +
                "room_hotel_id, " +
                "room_season_id, " +
                "room_pension_id, " +
                "room_stock, " +
                "prc_for_child, " +
                "prc_for_adult, " +
                "room_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, room.getRoomHotelId());
            System.out.println(room.getRoomHotelId());
            pr.setInt(2,room.getSeason().getSeasonId());
            System.out.println(room.getSeason().getSeasonId());
            pr.setInt(3,room.getPension().getPensionId());
            System.out.println(room.getPension().getPensionId());
            pr.setInt(4, room.getRoomStock());
            System.out.println(room.getRoomStock());
            pr.setInt(5, room.getPriceChild());
            System.out.println(room.getPriceChild());
            pr.setInt(6, room.getPriceAdult());
            System.out.println(room.getPriceAdult());
            pr.setString(7, String.valueOf(room.getType()));
            System.out.println(String.valueOf(room.getType()));
            pr.executeUpdate();
            ResultSet generatedKeys = pr.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Oda ekleme başarısız, oda id alınamadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }



    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomSeasonId(rs.getInt("room_season_id"));
        room.setRoomPensionId(rs.getInt("room_pension_id"));
        room.setRoomHotelId(rs.getInt("room_hotel_id"));
        room.setRoomStock(rs.getInt("room_stock"));
        room.setPriceAdult(rs.getInt("prc_for_adult"));
        room.setPriceChild(rs.getInt("prc_for_child"));
        room.setType(Room.Type.valueOf(rs.getString("room_type")));
        return room;
    }




}
