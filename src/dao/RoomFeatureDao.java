package dao;

import core.Database;
import core.Helper;
import entity.HotelFeature;
import entity.Pension;
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

            String featureKey = roomFeature.getRoomFeature().keySet().toString();
            String featureKeyCleaned = featureKey.substring(1, featureKey.length() - 1);

            String featureValue = roomFeature.getRoomFeature().values().toString();
            String featureValueCleaned = featureValue.substring(1, featureValue.length() - 1);

            pr.setInt(1, roomFeature.getRoomFeatureRoomId());
            pr.setString(2, featureKeyCleaned);
            pr.setString(3, featureValueCleaned);
            pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<RoomFeature> getFeaturesByRoomId(int roomId) {
        ArrayList<RoomFeature> selectedFeatures = new ArrayList<>();
        String query = "SELECT * FROM public.room_features WHERE room_feature_room_id = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, roomId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                RoomFeature feature = new RoomFeature();
                feature.setRoomFeatureId(rs.getInt("room_feature_id"));
                feature.setRoomFeatureRoomId(rs.getInt("room_feature_room_id"));
                feature.addRoomFeature(rs.getString("feature_name"), rs.getString("feature_value"));
                selectedFeatures.add(feature);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedFeatures;
    }

    public RoomFeature match(ResultSet rs) throws SQLException {
        RoomFeature roomFeature = new RoomFeature();
        roomFeature.setRoomFeatureId(rs.getInt("room_feature_id"));
        roomFeature.setRoomFeatureRoomId(rs.getInt("room_feature_room_id"));
        return roomFeature;
    }

    public boolean delete(int roomId) {
        String query = "DELETE FROM public.room_features WHERE room_feature_room_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, roomId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update2(ArrayList<RoomFeature> selectedRoomFeatures, int roomId) {
        String insertQuery = "INSERT INTO public.room_features (room_feature_room_id, feature_name, feature_value) " +
                "SELECT ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM public.room_features WHERE room_feature_room_id = ? AND feature_name = ? AND feature_value = ?)";

        String selectAllQuery = "SELECT * FROM public.room_features WHERE room_feature_room_id= ?";

        String deleteQuery = "DELETE FROM public.room_features WHERE room_feature_id = ? AND feature_name = ?";

        try {
            // Ekleme işlemleri
            for (RoomFeature roomFeature : selectedRoomFeatures) {

                String featureKey = roomFeature.getRoomFeature().keySet().toString();
                String featureKeyCleaned = featureKey.substring(1, featureKey.length() - 1);

                String featureValue = roomFeature.getRoomFeature().values().toString();
                String featureValueCleaned = featureValue.substring(1, featureValue.length() - 1);

                PreparedStatement insertStatement = this.connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, roomId);
                insertStatement.setString(2, featureKeyCleaned);
                insertStatement.setString(3, featureValueCleaned);
                insertStatement.setInt(4, roomId);
                insertStatement.setString(5, featureKeyCleaned);
                insertStatement.setString(6, featureValueCleaned);

                insertStatement.executeUpdate();
            }

            // Veritabanından tüm ilgili satırları al
            PreparedStatement selectAllStatement = this.connection.prepareStatement(selectAllQuery);
            selectAllStatement.setInt(1, roomId);
            ResultSet resultSet = selectAllStatement.executeQuery();


            // Silme işlemi
            while (resultSet.next()) {
                String featureName = resultSet.getString("feature_name");
                String featureValue = resultSet.getString("feature_value");

                boolean found = false;
                for (RoomFeature roomFeature : selectedRoomFeatures) {
                    String featureKey2 = roomFeature.getRoomFeature().keySet().toString();
                    String featureKeyCleaned2 = featureKey2.substring(1, featureKey2.length() - 1);


                    if (featureName.equals(featureKeyCleaned2)) {
                        found = true;
                        break;
                    }
                }

                // Eğer veritabanındaki satır listede yoksa sil
                if (!found) {
                    PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, roomId);
                    deleteStatement.setString(2, featureName);
                    deleteStatement.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
