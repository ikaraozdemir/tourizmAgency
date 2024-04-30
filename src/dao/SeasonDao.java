package dao;

import core.Database;
import core.Helper;
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
        Season season = new Season();
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

    public boolean deleteByHotelId(int hotelId) {
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

    public boolean delete(int seasonId) {
        String query = "DELETE FROM public.season WHERE season_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, seasonId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean update2(ArrayList<Season> selectedSeasons, int hotelId, ArrayList<Integer> seasonIds) {
        String query = "INSERT INTO public.season (season_hotel_id, season_start, season_end, season_name) " +
                "SELECT ?, ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM public.season s " +
                "                  WHERE s.season_hotel_id = ? " +
                "                  AND s.season_start = ? " +
                "                  AND s.season_end = ? " +
                "                  AND s.season_name = ?)";

        String selectAllQuery = "SELECT * FROM public.season WHERE season_hotel_id = ?";

        String deleteQuery = "DELETE FROM public.season WHERE season_hotel_id = ? AND season_name = ?";



        try {

            for (Season season : selectedSeasons) {
                PreparedStatement insertstatement = this.connection.prepareStatement(query);
                insertstatement.setInt(1, season.getSeasonHotelId());
                insertstatement.setDate(2, Date.valueOf(season.getStrtDate()));
                insertstatement.setDate(3, Date.valueOf(season.getEndDate()));
                insertstatement.setString(4, season.getSeasonName());
                insertstatement.setInt(5, season.getSeasonHotelId());
                insertstatement.setDate(6, Date.valueOf(season.getStrtDate()));
                insertstatement.setDate(7, Date.valueOf(season.getEndDate()));
                insertstatement.setString(8, season.getSeasonName());
                insertstatement.executeUpdate();

            }

            PreparedStatement selectAllStatement = this.connection.prepareStatement(selectAllQuery);
            selectAllStatement.setInt(1, hotelId);
            ResultSet resultSet = selectAllStatement.executeQuery();


            while (resultSet.next()) {
                String seasonName = resultSet.getString("season_name");
                int seasonId = resultSet.getInt("season_id");
                boolean found = false;
                for (Season season : selectedSeasons) {
                    if (seasonName.equals(season.getSeasonName()) ) {
                        found = true;
                        break;
                    }
                }

                // Eğer veritabanındaki satır listede yoksa sil
                if (!found && (seasonIds.isEmpty() || !seasonIds.contains(seasonId))) {
                    PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, hotelId);
                    deleteStatement.setString(2, seasonName);
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
