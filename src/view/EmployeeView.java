package view;

import business.*;
import core.Helper;
import entity.Hotel;
import entity.Room;
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
    private JTable tbl_emp_rooms;
    private JTable tbl_emp_reserv;
    private JTextField fld_room_checkin;
    private JTextField fld_room_checkout;
    private JTextField fld_room_hotel_filter;
    private JTextField fld_room_city_filter;
    private JTextField fld_room_adult_filter;
    private JTextField fld_room_child_filter;
    private JButton btn_room_filter;
    private JButton btn_room_clear;
    private Hotel hotel;
    private User user;
    private final DefaultTableModel tmbl_hotels = new DefaultTableModel();
    private final DefaultTableModel tmbl_seasons = new DefaultTableModel();
    private final DefaultTableModel tmbl_rooms = new DefaultTableModel();
    private final DefaultTableModel tmbl_reserv = new DefaultTableModel();
    private final HotelManager hotelManager;
    private final HotelFeatureManager hotelFeatureManager;
    private final PensionManager pensionManager;
    private final SeasonManager seasonManager;
    private final RoomManager roomManager;
    private final RoomFeatureManager roomFeatureManager;
    private Object[] col_hotel;
    private Object[] col_season;
    private Object[] col_room;
    private Object[] col_reserv;
    private JPopupMenu hotel_menu;
    private JPopupMenu room_menu;
    private Object[] col_searched_room = new Object[]{"ID", "Otel","Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
            "Oda Stoğu", "Oda Tipi", "Oda Özellikleri", "Toplam Gün", "Yetişkin İçin Fiyat", "Çocuk İçin Fiyat", "Toplam Fiyat"};

    public EmployeeView (User user) {
        this.hotelManager = new HotelManager();
        this.hotelFeatureManager = new HotelFeatureManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.roomManager = new RoomManager();
        this.roomFeatureManager = new RoomFeatureManager();
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
        loadRoomTable();
        loadRoomComponent();
    }

    public void loadHotelTable() {
        this.col_hotel = new Object[]{"ID", "Şehir", "Bölge", "İsim", "Sabit Telefon", "Mail", "Yıldız Sayısı",
                "Adres", "Pansiyon Tipleri", "Tesis Özellikleri",  "Sezonlar" };
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.findHotelsWithFeatures());
        createTable(this.tmbl_hotels, this.tbl_emp_hotels, col_hotel, hotelList);
    }

    public void loadSeasonTable() {
        this.col_season = new Object[]{"ID", "Sezon Başlangıcı", "Sezon Bitişi", "Sezon İsmi"};
        ArrayList<Object[]> seasonList = this.seasonManager.getForTable(col_season.length, this.seasonManager.findAll());
        createTable(this.tmbl_seasons, this.tbl_emp_seasons, col_season, seasonList);
    }

    public void loadRoomTable() {
        this.col_room = new Object[]{"ID", "Otel","Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
                "Oda Stoğu", "Yetişkin İçin Fiyat", "Çocuk İçin Fiyat", "Oda Tipi", "Oda Özellikleri"};
        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room.length, this.roomManager.getRoomsWithDetails(-1));
        createTable(this.tmbl_rooms, this.tbl_emp_rooms, col_room, roomList);
    }

//    public void loadRezervationTable(ArrayList<Object[]> roomReservationRow) {
//        this.col_reserv = new Object[]{"ID", "Otel İsmi","Oda Tipi", "Check-in", "Check-out", "Misafir TC",
//                "Misafir İsim", "Misafir Numara", "Misafir Mail", "Yetişkin Misafir", "Çocuk Misafir", "Total Fiyat"};
//        createTable(this.tmbl_reserv, this.tbl_emp_reserv, col_reserv, roomReservationRow);
//    }




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
                    loadRoomTable();

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
        this.hotel_menu.add("Oda Ekle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_emp_hotels,0);
            RoomView roomView = new RoomView(new Room(),this.hotelManager.getById(selectHotelId));
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();
                }
            });
        });
        this.tbl_emp_hotels.setComponentPopupMenu(hotel_menu);

        this.btn_room_filter.addActionListener(e -> {
            ArrayList<Room> roomList = this.roomManager.searchForReservation(
                    this.fld_room_checkin.getText(),
                    this.fld_room_checkout.getText(),
                    this.fld_room_hotel_filter.getText(),
                    this.fld_room_city_filter.getText(),
                    Integer.parseInt(this.fld_room_adult_filter.getText()),
                    Integer.parseInt(this.fld_room_child_filter.getText())
            );
            ArrayList<Object[]> roomReservationRow = this.roomManager.getForSearchedRoomTable(col_searched_room.length, roomList);
            FilteredRoomView filteredRoomView = new FilteredRoomView();
            filteredRoomView.loadSearchedRoomTable(roomReservationRow);
        });
    }

    private void loadRoomComponent() {
        tableRowSelect(this.tbl_emp_rooms);
        this.room_menu = new JPopupMenu();

        this.room_menu.add("Güncelle").addActionListener(e -> {

            int selectHotelId = this.getTableSelectedRow(tbl_emp_rooms, 2);
            int selectRoomId = this.getTableSelectedRow(tbl_emp_rooms, 0);

            RoomView roomView = new RoomView(this.roomManager.getById(selectRoomId),this.hotelManager.getById(selectHotelId));
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();

                }
            });
        });
        this.room_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectRoomId = this.getTableSelectedRow(tbl_emp_rooms, 0);
                if (this.roomManager.delete(selectRoomId) &&
                        this.roomFeatureManager.delete(selectRoomId)){

                    Helper.showMessage("done");
                    loadRoomTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_emp_rooms.setComponentPopupMenu(room_menu);

    }
}
