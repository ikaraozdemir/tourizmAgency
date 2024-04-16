package view;

import business.*;
import core.Helper;
import entity.Hotel;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EmployeeView extends Layout{
    private JPanel container;
    private JButton btn_emp_logout;
    private JTabbedPane tabbedPane1;
    private JTable tbl_emp_hotels;
    private JLabel lbl_hotel_welcome;
    private JTable tbl_emp_seasons;
    private JTable table1;
    private JTable table2;
    private Hotel hotel;
    private User user;
    private final DefaultTableModel tmbl_hotels = new DefaultTableModel();
    private final DefaultTableModel tmbl_seasons = new DefaultTableModel();
    private final HotelManager hotelManager;
    private final HotelFeatureManager hotelFeatureManager;
    private final PensionManager pensionManager;
    private final SeasonManager seasonManager;
    private Object[] col_hotel;
    private Object[] col_season;
    private JPopupMenu hotel_menu;

    public EmployeeView (User user) {
        this.hotelManager = new HotelManager();
        this.hotelFeatureManager = new HotelFeatureManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.user = user;
        this.add(container);
        this.guiInitialize(1000,500);
        if (this.user == null) {
            dispose();
        }
        this.lbl_hotel_welcome.setText("Hoşgeldiniz " + this.user.getName());
        loadHotelTable();
        loadHotelComponent();
        loadSeasonTable();
    }

    public void loadHotelTable() {
        this.col_hotel = new Object[]{"ID", "Şehir", "Bölge", "İsim", "Sabit Telefon", "Mail", "Yıldız Sayısı",
                "Adres", "Pansiyon Özellikleri", "Tesis Özellikleri",  "Sezonlar" };
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.findHotelsWithFeatures());
        createTable(this.tmbl_hotels, this.tbl_emp_hotels, col_hotel, hotelList);
    }

    public void loadSeasonTable() {
        this.col_season = new Object[]{"ID", "Sezon Başlangıcı", "Sezon Bitişi", "Sezon İsmi"};
        ArrayList<Object[]> seasonList = this.seasonManager.getForTable(col_season.length, this.seasonManager.findAll());
        createTable(this.tmbl_seasons, this.tbl_emp_seasons, col_season, seasonList);
    }

    private void loadHotelComponent() {
        tableRowSelect(this.tbl_emp_hotels);

        this.hotel_menu = new JPopupMenu();
        this.hotel_menu.add("Yeni").addActionListener(e -> {
            HotelView hotelView = new HotelView(new Hotel());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();

                }
            });
        });

       this.hotel_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_emp_hotels,0);
            HotelView hotelView = new HotelView(this.hotelManager.getById(selectHotelId));
           hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();

                }
            });
        });
        this.hotel_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectHoteltId = this.getTableSelectedRow(tbl_emp_hotels,0);
                if (this.hotelManager.delete(selectHoteltId) &&
                        this.hotelFeatureManager.delete(selectHoteltId) &&
                        this.pensionManager.delete(selectHoteltId) &&
                        this.seasonManager.delete(selectHoteltId)) {
                    Helper.showMessage("done");
                    loadHotelTable();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_emp_hotels.setComponentPopupMenu(hotel_menu);


    }




}
