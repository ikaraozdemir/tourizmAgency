package dao;

import core.Database;
import entity.HotelFeature;
import entity.Pension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionDao {
    private Connection connection;

    public PensionDao() {
        this.connection = Database.getInstance();
    }

    public Pension getById(int id) {
        Pension obj = null;
        String query = "SELECT * FROM public.pension WHERE pension_id = ?";
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

    public ArrayList<Pension> findAll() {
        return this.selectByQuery("SELECT * FROM public.pension ORDER BY pension_id ASC");
    }

    public ArrayList<Pension> selectByQuery(String query) {
        ArrayList<Pension> pensions = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                pensions.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensions;
    }

    public Pension match(ResultSet rs) throws SQLException {
        Pension pension = new Pension();
        pension.setPensionId(rs.getInt("pension_id"));
        pension.setPensionHotelId(rs.getInt("pension_hotel_id"));
        pension.setPensionType(rs.getString("pension_types"));

        return pension;
    }

    public boolean delete(int hotelId) {
        String query = "DELETE FROM public.pension WHERE pension_hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotelId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(Pension pension) {
        String query = "INSERT INTO public.pension (" +
                "pension_hotel_id, " +
                "pension_types) " +
                "VALUES (?, ?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);

            pr.setInt(1, pension.getPensionHotelId());
            pr.setString(2, pension.getPensionType());
            pr.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Pension> getPensionsByHotelId(int hotelId) {
        ArrayList<Pension> selectedPensions = new ArrayList<>();
        String query = "SELECT * FROM public.pension WHERE pension_hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Pension pension = new Pension();
                pension.setPensionId(rs.getInt("pension_id"));
                pension.setPensionType(rs.getString("pension_types"));
                pension.setPensionHotelId(rs.getInt("pension_hotel_id"));
                selectedPensions.add(pension);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedPensions;

    }

    public boolean update(Pension pension) {
        String query = "UPDATE public.pension SET " +
                "pension_hotel_id = ? , " +
                "pension_types = ? , " +
                "WHERE pension_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, pension.getPensionHotelId());
            pr.setString(2, pension.getPensionType());
            pr.setInt(3, pension.getPensionId());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
