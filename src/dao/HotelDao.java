package dao;
import core.Database;
import entity.Hotel;
import entity.HotelFeature;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {
    private Connection connection;

    public HotelDao() {
        this.connection = Database.getInstance();
    }

    public Hotel getById(int id) {
        Hotel hotel = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                hotel = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    public ArrayList<Hotel> findAll() {
        return this.selectByQuery("SELECT * FROM public.hotel ORDER BY hotel_id ASC");
    }

    public ArrayList<Hotel> findHotelsWithFeatures() {
        return this.selectByQuery(
                "SELECT hotel.*, " +
                "ARRAY_AGG(DISTINCT pension.pension_types) AS pension_types, " +
                "ARRAY_AGG(DISTINCT hotel_features.hotel_features) AS hotel_features, " +
                "ARRAY_AGG(DISTINCT season.season_name) AS season_names " +
                "FROM hotel " +
                "LEFT JOIN pension ON hotel.hotel_id = pension.pension_hotel_id " +
                "LEFT JOIN hotel_features ON hotel.hotel_id = hotel_features.hotel_features_hotel_id " +
                "LEFT JOIN season ON hotel.hotel_id = season.season_hotel_id " +
                "GROUP BY hotel.hotel_id;");
    }

    public ArrayList<Hotel> selectByQuery(String query) {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                hotelList.add(this.match(rs));
//                System.out.println(hotelList.get(0).getPensionTypes().get(1).getPensionType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }




    public boolean save(Hotel hotel) {
        String query = "INSERT INTO public.hotel " +
                "(" +
                "hotel_city," +
                "hotel_region," +
                "hotel_name," +
                "hotel_phno," +
                "hotel_mail," +
                "hotel_star," +
                "hotel_address" +
                ")" +
                "VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setString(1, hotel.getCity());
            pr.setString(2, hotel.getRegion());
            pr.setString(3, hotel.getHotelName());
            pr.setString(4, hotel.getHotelPhno());
            pr.setString(5, hotel.getHotelMail());
            pr.setString(6, hotel.getStar());
            pr.setString(7, hotel.getHotelAddress());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int saveForId(Hotel hotel) {
        int generatedId = 0;
        String query = "INSERT INTO public.hotel " +
                "(" +
                "hotel_city," +
                "hotel_region," +
                "hotel_name," +
                "hotel_phno," +
                "hotel_mail," +
                "hotel_star," +
                "hotel_address" +
                ")" +
                "VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, hotel.getCity());
            pr.setString(2, hotel.getRegion());
            pr.setString(3, hotel.getHotelName());
            pr.setString(4, hotel.getHotelPhno());
            pr.setString(5, hotel.getHotelMail());
            pr.setString(6, hotel.getStar());
            pr.setString(7, hotel.getHotelAddress());
            pr.executeUpdate();
            ResultSet generatedKeys = pr.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Otel ekleme başarısız, otel id alınamadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }


    public boolean update(Hotel hotel) {
        String query = "UPDATE public.hotel SET " +
                "hotel_city = ? , " +
                "hotel_region = ? , " +
                "hotel_name = ? , " +
                "hotel_phno = ? , " +
                "hotel_mail = ? , " +
                "hotel_star = ? , " +
                "hotel_address = ? " +
                "WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotel.getHotelId());
            pr.setString(2, hotel.getCity());
            pr.setString(3, hotel.getRegion());
            pr.setString(4, hotel.getHotelName());
            pr.setString(5, hotel.getHotelPhno());
            pr.setString(6, hotel.getHotelMail());
            pr.setString(7, hotel.getStar());
            pr.setString(8, hotel.getHotelAddress());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Hotel match(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();

        hotel.setHotelId(rs.getInt("hotel_id"));
        hotel.setCity(rs.getString("hotel_city"));
        hotel.setRegion(rs.getString("hotel_region"));
        hotel.setHotelName(rs.getString("hotel_name"));
        hotel.setHotelPhno(rs.getString("hotel_phno"));
        hotel.setHotelMail(rs.getString("hotel_mail"));
        hotel.setStar(rs.getString("hotel_star"));
        hotel.setHotelAddress(rs.getString("hotel_address"));

        Array pensionTypesArray = rs.getArray("pension_types");
        if (pensionTypesArray != null) {
            String[] pensionTypes = (String[]) pensionTypesArray.getArray();
            ArrayList<Pension> pensionList = new ArrayList<>();
            for (String pensionType : pensionTypes) {
                Pension pension =new Pension();
                pension.setPensionType(pensionType);
//                System.out.println(pension.getPensionType());
                pensionList.add(pension);
            }
            hotel.setPensionTypes(pensionList);
        }

        // season_names sütununu işleme
        Array seasonNamesArray = rs.getArray("season_names");
        if (seasonNamesArray != null) {
            String[] seasonNames = (String[]) seasonNamesArray.getArray();
            ArrayList<Season> seasonList = new ArrayList<>();
            for (String seasonName : seasonNames) {
                Season season = new Season();
                season.setSeasonName(seasonName);
                seasonList.add(season);
            }
            hotel.setSeasons(seasonList);
        }

        // hotel_features sütununu işleme
        Array hotelFeaturesArray = rs.getArray("hotel_features");
        if (hotelFeaturesArray != null) {
            String[] hotelFeatureList = (String[]) hotelFeaturesArray.getArray();
            ArrayList<HotelFeature> featureList = new ArrayList<>();
            for (String feature : hotelFeatureList) {
                HotelFeature hotelFeatures = new HotelFeature();
                hotelFeatures.setHotelFeature(feature);
                featureList.add(hotelFeatures);
            }
            hotel.setHotelFeatures(featureList);
        }

        return hotel;
    }
}

