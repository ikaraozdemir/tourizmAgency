package dao;

import core.Database;
import entity.Pension;
import entity.Season;

import java.sql.*;
import java.time.LocalDate;
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




}
