package dao;

import business.HotelManager;
import business.PensionManager;
import business.SeasonManager;
import core.Database;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class RoomDao {
//    private SeasonManager seasonManager;
//    private PensionManager pensionManager;
//    private HotelManager hotelManager;
    private Connection connection;

    public RoomDao() {
//        this.pensionManager = new PensionManager();
//        this.seasonManager = new SeasonManager();
//        this.hotelManager = new HotelManager();
        this.connection = Database.getInstance();
    }

    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
//                obj.setSeason(this.seasonManager.getById(obj.getRoomSeasonId()));
//                obj.setPension(this.pensionManager.getById(obj.getRoomPensionId()));
            }

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

    public boolean delete(int roomId) {
        String query = "DELETE FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, roomId);
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
            PreparedStatement pr = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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
                System.out.println(generatedKeys.getInt(1));
                generatedId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Oda ekleme başarısız, oda id alınamadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public ArrayList<Room> getRoomsWithDetails(int id) {
        ArrayList<Room> rooms = new ArrayList<>();
        String queryWhere = "";
        if(id != -1) {
            queryWhere = " WHERE room_id = " + id;
        }

        String query = "SELECT " +
                "r.room_id, " +
                "r.room_type, " +
                "r.room_stock, " +
                "r.prc_for_child, " +
                "r.prc_for_adult, " +
                "h.hotel_id, " +
                "h.hotel_name, " +
                "s.season_id, " +
                "s.season_start, " +
                "s.season_end, " +
                "s.season_name, " +
                "p.pension_id, " +
                "p.pension_types, " +
                "ARRAY_AGG(rf.feature_name) AS feature_names, " +
                "ARRAY_AGG(rf.feature_value) AS feature_values " +
                "FROM room r " +
                "INNER JOIN hotel h ON r.room_hotel_id = h.hotel_id " +
                "INNER JOIN season s ON r.room_season_id = s.season_id " +
                "INNER JOIN pension p ON r.room_pension_id = p.pension_id " +
                "LEFT JOIN room_features rf ON r.room_id = rf.room_feature_room_id " +
                queryWhere +
                " GROUP BY r.room_id, h.hotel_id, h.hotel_name, s.season_id, s.season_name, p.pension_id, p.pension_types";
        System.out.println(query);
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomStock(rs.getInt("room_stock"));
                room.setPriceAdult(rs.getInt("prc_for_adult"));
                room.setPriceChild(rs.getInt("prc_for_child"));
                room.setType(Room.Type.valueOf(rs.getString("room_type")));

                Hotel hotel = new Hotel();
                hotel.setHotelName(rs.getString("hotel_name"));
                hotel.setHotelId(rs.getInt("hotel_id"));
                room.setHotel(hotel);

                Season season = new Season();
                season.setSeasonName(rs.getString("season_name"));
                season.setStrtDate(rs.getDate("season_start").toLocalDate());
                season.setEndDate(rs.getDate("season_end").toLocalDate());
                room.setSeason(season);

                Pension pension = new Pension();
                pension.setPensionType(rs.getString("pension_types"));
                room.setPension(pension);

                // Separate feature names and values for each room
                ArrayList<RoomFeature> roomFeaturesList = new ArrayList<>();
                String[] featureNames = (String[]) rs.getArray("feature_names").getArray();
                String[] featureValues = (String[]) rs.getArray("feature_values").getArray();
                for (int i = 0; i < featureNames.length; i++) {
                    RoomFeature roomFeature = new RoomFeature();
                    roomFeature.addRoomFeature(featureNames[i], featureValues[i]);
                    roomFeaturesList.add(roomFeature);
                }
                room.setRoomFeatures(roomFeaturesList);

                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public boolean update(Room room) {
        String query = "UPDATE public.room SET " +
                "room_hotel_id = ? , " +
                "room_season_id = ? , " +
                "room_pension_id = ? , " +
                "room_stock = ? , " +
                "prc_for_child = ? , " +
                "prc_for_adult = ?, " +
                "room_type = ? " +
                "WHERE room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, room.getRoomHotelId());
            pr.setInt(2, room.getRoomSeasonId());
            pr.setInt(3, room.getRoomPensionId());
            pr.setInt(4, room.getRoomStock());
            pr.setInt(5, room.getPriceChild());
            pr.setInt(6, room.getPriceAdult());
            pr.setString(7, String.valueOf(room.getType()));
            pr.setInt(8, room.getRoomId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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
//        room.setHotel(this.hotelManager.getById(rs.getInt("room_hotel_id")));
//        room.setSeason(this.seasonManager.getById(rs.getInt("room_season_id")));
//        room.setPension(this.pensionManager.getById(rs.getInt("room_pension_id")));
        return room;
    }

}
