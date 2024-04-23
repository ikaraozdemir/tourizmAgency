package dao;

import core.Database;
import entity.HotelFeature;
import entity.Pension;
import entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SeasonDao {
    private Connection connection;

    public SeasonDao() {
        this.connection = Database.getInstance();
    }

    public Season getById(int id) {
        Season obj = null;
        String query = "SELECT * FROM public.season WHERE season_id = ?";
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
    public ArrayList<Season> findAll() {
        return this.selectByQuery("SELECT * FROM public.season ORDER BY season_id ASC");
    }

    public ArrayList<Season> selectByQuery(String query) {
        ArrayList<Season> seasons = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                seasons.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }

    public Season match(ResultSet rs) throws SQLException {
        Season season= new Season();
        season.setSeasonId(rs.getInt("season_id"));
        season.setSeasonHotelId(rs.getInt("season_hotel_id"));
        season.setStrtDate(LocalDate.parse(rs.getString("season_start")));
        season.setEndDate(LocalDate.parse(rs.getString("season_end")));
        season.setSeasonName(rs.getString("season_name"));

        return season;
    }

    public boolean save(Season season) {
        String query = "INSERT INTO public.season (" +
                "season_hotel_id, " +
                "season_start, " +
                "season_end, " +
                "season_name) " +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, season.getSeasonHotelId());
            pr.setDate(2, Date.valueOf(season.getStrtDate()));
            pr.setDate(3, Date.valueOf(season.getEndDate()));
            pr.setString(4, season.getSeasonName());

            pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Season> getSeasonsByHotelId(int hotelId) {
        ArrayList<Season> selectedSeasons = new ArrayList<>();
        String query = "SELECT * FROM public.season WHERE season_hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Season season = new Season();
                season.setSeasonId(rs.getInt("season_id"));
                season.setSeasonName(rs.getString("season_name"));
                season.setStrtDate(rs.getDate("season_start").toLocalDate());
                season.setEndDate(rs.getDate("season_end").toLocalDate());
                season.setSeasonHotelId(rs.getInt("season_hotel_id"));
                selectedSeasons.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedSeasons;

    }

//    public boolean update(Season season) {
//        String query = "UPDATE public.season SET " +
//                "season_hotel_id = ? , " +
//                "season_start = ? , " +
//                "season_end = ? , " +
//                "season_name = ? , " +
//                "WHERE season_id = ?";
//        try {
//            PreparedStatement pr = this.connection.prepareStatement(query);
//            pr.setInt(1, season.getSeasonHotelId());
//            pr.setDate(2, Date.valueOf(season.getStrtDate()));
//            pr.setDate(3, Date.valueOf(season.getEndDate()));
//            pr.setString(4, season.getSeasonName());
//            pr.setString(5, season.getSeasonId());
//
//
//            return pr.executeUpdate() != -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    public boolean delete(int hotelId) {
        String query = "DELETE FROM public.season WHERE season_hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotelId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



}
