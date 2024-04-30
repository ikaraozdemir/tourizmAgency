package dao;

import core.Database;
import core.Helper;
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


    public boolean update2(ArrayList<Pension> selectedPensions, int hotelId, ArrayList<Integer> pensionIds) {
        String insertQuery = "INSERT INTO public.pension (pension_hotel_id, pension_types) " +
                "SELECT ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM public.pension WHERE pension_hotel_id = ? AND pension_types = ?)";

        String selectAllQuery = "SELECT * FROM public.pension WHERE pension_hotel_id = ?";

        String deleteQuery = "DELETE FROM public.pension WHERE pension_hotel_id = ? AND pension_types = ?";

        try {
            // Ekleme işlemleri
            for (Pension pension : selectedPensions) {
                PreparedStatement insertStatement = this.connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, hotelId);
                insertStatement.setString(2, pension.getPensionType());
                insertStatement.setInt(3, hotelId);
                insertStatement.setString(4, pension.getPensionType());
                insertStatement.executeUpdate();
            }

            // Veritabanından tüm ilgili satırları al
            PreparedStatement selectAllStatement = this.connection.prepareStatement(selectAllQuery);
            selectAllStatement.setInt(1, hotelId);
            ResultSet resultSet = selectAllStatement.executeQuery();


            // Silme işlemi
            while (resultSet.next()) {
                String pensionType = resultSet.getString("pension_types");
                int pensionId = resultSet.getInt("pension_id");
                boolean found = false;
                for (Pension pension : selectedPensions) {
                    if (pensionType.equals(pension.getPensionType())) {
                        found = true;
                        break;
                    }

                }

                // Eğer veritabanındaki satır listede yoksa sil
                if (!found && (pensionIds.isEmpty() || !pensionIds.contains(pensionId))) {
                    PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, hotelId);
                    deleteStatement.setString(2, pensionType);
                    deleteStatement.executeUpdate();
                }
            }


            for (int pensionId : pensionIds) {
                boolean found2 = true;
                for (Pension pension : selectedPensions) {
                    if (pension.getPensionId() == pensionId) {
                        found2 = false;
                        break;
                    }
                }
                if (!found2) {
                    Helper.showMessage("cannotUpdate");
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
