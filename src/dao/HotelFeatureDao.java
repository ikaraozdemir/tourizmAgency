package dao;



import core.Database;
import entity.HotelFeature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelFeatureDao {
    private Connection connection;

    public HotelFeatureDao() {
        this.connection = Database.getInstance();
    }

    public HotelFeature getById(int id) {
        HotelFeature obj = null;
        String query = "SELECT * FROM public.hotel_features WHERE hotel_features_id = ?";
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

    public ArrayList<HotelFeature> findAll() {
        return this.selectByQuery("SELECT * FROM public.hotel_features ORDER BY hotel_features_id ASC");
    }

    public ArrayList<HotelFeature> selectByQuery(String query) {
        ArrayList<HotelFeature> hotelFeatures = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                hotelFeatures.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelFeatures;
    }

    public HotelFeature match(ResultSet rs) throws SQLException {
        HotelFeature hotelFeature= new HotelFeature();
        hotelFeature.setHotelFeatureHotelId(rs.getInt("hotel_features_id"));
        hotelFeature.setHotelFeatureHotelId(rs.getInt("hotel_features_hotel_id"));
        hotelFeature.setHotelFeature(rs.getString("hotel_features"));

        return hotelFeature;
    }

//    public boolean update(Car car) {
//        String query = "UPDATE public.car SET " +
//                "car_model_id = ? , " +
//                "car_color = ? , " +
//                "car_km = ? , " +
//                "car_plate = ? " +
//                "WHERE car_id = ?";
//        try {
//            PreparedStatement pr = this.connection.prepareStatement(query);
//            pr.setInt(1, car.getModel_id());
//            pr.setString(2, car.getColor().toString());
//            pr.setInt(3, car.getKm());
//            pr.setString(4, car.getPlate());
//            pr.setInt(5, car.getId());
//            return pr.executeUpdate() != -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    public boolean save(HotelFeature hotelFeature) {
        String query = "INSERT INTO public.hotel_features (" +
                "hotel_features_hotel_id, " +
                "hotel_features) " +
                "VALUES (?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

                pr.setInt(1, hotelFeature.getHotelFeatureHotelId());
                pr.setString(2, hotelFeature.getHotelFeature());
                pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public boolean delete(int carId) {
//        String query = "DELETE FROM public.car WHERE car_id = ?";
//        try {
//            PreparedStatement pr = this.connection.prepareStatement(query);
//            pr.setInt(1, carId);
//            return pr.executeUpdate() != -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
