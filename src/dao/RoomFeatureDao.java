package dao;

import core.Database;
import entity.HotelFeature;
import entity.Room;
import entity.RoomFeature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFeatureDao {
    private Connection connection;

    public RoomFeatureDao() {
        this.connection = Database.getInstance();
    }

    public RoomFeature getById(int id) {
        RoomFeature obj = null;
        String query = "SELECT * FROM public.room_features WHERE room_feature_id = ?";
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

    public ArrayList<RoomFeature> findAll() {
        return this.selectByQuery("SELECT * FROM public.room_features ORDER BY room_feature_id ASC");
    }

    public ArrayList<RoomFeature> selectByQuery(String query) {
        ArrayList<RoomFeature> roomFeatures = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
               roomFeatures.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomFeatures;
    }

    public boolean save(RoomFeature roomFeature) {
        String query = "INSERT INTO public.room_features (" +
                "room_feature_room_id, " +
                "feature_name, " +
                "feature_value) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, roomFeature.getRoomFeatureRoomId());
            pr.setString(2, roomFeature.getRoomFeature().keySet().toString());
            pr.setString(3,roomFeature.getRoomFeature().values().toString());
            pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int roomId) {
        String query = "DELETE FROM public.room_features WHERE room_features_room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, roomId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public RoomFeature match(ResultSet rs) throws SQLException {
        RoomFeature roomFeature= new RoomFeature();
        roomFeature.setRoomFeatureId(rs.getInt("room_feature_id"));
        roomFeature.setRoomFeatureRoomId(rs.getInt("room_feature_room_id"));
//        roomFeature.setFeatureName(rs.getString("feature_name"));
//        roomFeature.setFeatureValue(rs.getString("feature_value"));

        return roomFeature;
    }

}
