package view;

import business.*;
import core.Helper;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeView extends Layout {
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
    private final ReservationManager reservationManager;
    private String checkIn;
    private String checkOut;
    private String child;
    private String adult;
    ArrayList<Room> roomList;
    private final Object[] col_searched_room = new Object[]{"ID", "Otel", "Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
            "Oda Stoğu", "Oda Tipi", "Oda Özellikleri", "Toplam Gün", "Yetişkin İçin Fiyat", "Çocuk İçin Fiyat", "Toplam Fiyat"};


    public EmployeeView(User user) {
        this.hotelManager = new HotelManager();
        this.hotelFeatureManager = new HotelFeatureManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.roomManager = new RoomManager();
        this.roomFeatureManager = new RoomFeatureManager();
        this.reservationManager = new ReservationManager();
        this.user = user;
        this.add(container);
        this.guiInitialize(1000, 500);

        this.fld_room_checkin.setText("01/01/2024");
        this.fld_room_checkout.setText("01/01/2024");

        if (this.user == null) {
            dispose();
        }
        this.lbl_hotel_welcome.setText("<html><b>Hoşgeldiniz</b> " + this.user.getName() + "</html>");

        loadHotelTable();
        loadHotelComponent();

        loadSeasonTable();
        loadSeasonComponent();

        loadRoomTable();
        loadRoomComponent();

        loadReservationTable();
        loadReservationComponent();

        loadComponent();
    }

    private void loadComponent() {
        this.btn_emp_logout.addActionListener(e -> {
            dispose();
            new LoginView();
        });
    }

    public void loadHotelTable() {
        Object[] col_hotel = new Object[]{"ID", "Şehir", "Bölge", "İsim", "Sabit Telefon", "Mail", "Yıldız Sayısı",
                "Adres", "Pansiyon Tipleri", "Tesis Özellikleri", "Sezonlar"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.findHotelsWithFeatures());
        createTable(this.tmbl_hotels, this.tbl_emp_hotels, col_hotel, hotelList);
    }

    public void loadSeasonTable() {
        Object[] col_season = new Object[]{"ID", "Otel", "Sezon Başlangıcı", "Sezon Bitişi", "Sezon İsmi"};
        ArrayList<Object[]> seasonList = this.seasonManager.getForTable(col_season.length, this.seasonManager.findAll());
        createTable(this.tmbl_seasons, this.tbl_emp_seasons, col_season, seasonList);
    }

    public void loadRoomTable() {
        Object[] col_room = new Object[]{"ID", "Otel", "Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
                "Oda Stoğu", "Yetişkin Gecelik Fiyat (TL)", "Çocuk Gecelik Fiyat (TL)", "Oda Tipi", "Oda Özellikleri"};

        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room.length, this.roomManager.getRoomsWithDetails(-1,false));
        createTable(this.tmbl_rooms, this.tbl_emp_rooms, col_room, roomList);
    }

    public void loadReservationTable() {
        Object[] col_reserv = new Object[]{"ID", "Otel İsmi", "Oda Tipi", "Check-in", "Check-out", "Misafir TC No",
                "Misafir İsim", "Misafir Numara", "Misafir Mail", "Yetişkin Misafir", "Çocuk Misafir", "Total Fiyat (TL)"};
        ArrayList<Object[]> reservList = this.reservationManager.getForTable(col_reserv.length, this.reservationManager.findAll());
        createTable(this.tmbl_reserv, this.tbl_emp_reserv, col_reserv, reservList);
    }

    private void loadHotelComponent() {
        tableRowSelect(this.tbl_emp_hotels);

        JPopupMenu hotel_menu = new JPopupMenu();
        hotel_menu.add("Yeni").addActionListener(e -> {
            HotelView hotelView = new HotelView(new Hotel());
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                    loadSeasonTable();
                }
            });
        });

        hotel_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_emp_hotels, 0);

            if (this.reservationManager.getByHotelId(selectHotelId) != null) {
                Helper.showMessage("cannotDelete");
            }else{
                HotelView hotelView = new HotelView(this.hotelManager.getById(selectHotelId));
                hotelView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {

                        loadHotelTable();
                        loadSeasonTable();
                        loadRoomTable();

                    }
                });
            }
        });

        hotel_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectHotelId = this.getTableSelectedRow(tbl_emp_hotels, 0);
                if (!(hotelFeatureManager.getFeaturesByHotelId(selectHotelId).isEmpty())) {
                    this.hotelFeatureManager.delete(selectHotelId);
                }

                if (!(pensionManager.getPensionsByHotelId(selectHotelId).isEmpty())) {
                    this.pensionManager.delete(selectHotelId);
                }

                if (!(seasonManager.getSeasonsByHotelId(selectHotelId).isEmpty())) {
                    this.seasonManager.deleteByHotelId(selectHotelId);
                }

//                if (roomManager.getByHotelId(selectHotelId) != null) {
//                    roomFeatureManager.delete(roomManager.getByHotelId(selectHotelId).get(0).getRoomId());
//                    this.roomManager.deleteByHotelId(selectHotelId);
//                }

                if (reservationManager.getByHotelId(selectHotelId) != null) {
                    this.reservationManager.deleteByHotelId(selectHotelId);
                }

                if (this.hotelManager.delete(selectHotelId)) {
                    Helper.showMessage("done");
                    loadHotelTable();
                    loadSeasonTable();
                    loadRoomTable();
                    loadReservationTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        hotel_menu.add("Oda Ekle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_emp_hotels, 0);
            RoomView roomView = new RoomView(new Room(), this.hotelManager.getById(selectHotelId));
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();
                }
            });
        });
        this.tbl_emp_hotels.setComponentPopupMenu(hotel_menu);

        this.btn_room_filter.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_room_checkin, fld_room_checkout,
                    fld_room_adult_filter, fld_room_child_filter})) {
                Helper.showMessage("fillSomeAreas");
                return;
            }

            this.roomList = this.roomManager.searchForReservation(
                    this.fld_room_checkin.getText(),
                    this.fld_room_checkout.getText(),
                    this.fld_room_hotel_filter.getText(),
                    this.fld_room_city_filter.getText(),
                    Integer.parseInt(this.fld_room_adult_filter.getText()),
                    Integer.parseInt(this.fld_room_child_filter.getText())
            );

            ArrayList<Object[]> roomReservationRow = this.roomManager.getForSearchedRoomTable(col_searched_room.length, roomList);
            this.adult = fld_room_adult_filter.getText();
            this.child = fld_room_child_filter.getText();
            this.checkIn = fld_room_checkin.getText();
            this.checkOut = fld_room_checkout.getText();

            FilteredRoomView filteredRoomView = new FilteredRoomView(adult, child, checkIn, checkOut);
            filteredRoomView.loadSearchedRoomTable(roomReservationRow);
            filteredRoomView.loadSearchedRoomComponent(roomList);
            filteredRoomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();
                    loadReservationTable();
                }
            });

        });
    }

    private void loadSeasonComponent() {
        tableRowSelect(this.tbl_emp_seasons);
        JPopupMenu season_menu = new JPopupMenu();

        season_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {

                int selectSeasonId = this.getTableSelectedRow(tbl_emp_seasons, 0);
                int roomId = roomManager.getBySeasonId(selectSeasonId).getRoomId();
                if (roomManager.getBySeasonId(selectSeasonId) != null) {

                    if (this.reservationManager.getByRoomId(roomId) != null) {
                        Helper.showMessage("cannotDelete");
                    } else {
                        if (roomManager.getBySeasonId(selectSeasonId) != null) {
                            this.roomFeatureManager.delete(roomId);
                            this.roomManager.deleteBySeasonId(selectSeasonId);
                        }

                        if (this.seasonManager.delete(selectSeasonId)) {
                            Helper.showMessage("done");
                            loadSeasonTable();
                            loadRoomTable();
                            loadReservationTable();
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
        this.tbl_emp_seasons.setComponentPopupMenu(season_menu);
    }

    private void loadRoomComponent() {
        tableRowSelect(this.tbl_emp_rooms);
        JPopupMenu room_menu = new JPopupMenu();

        room_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_emp_rooms, 2);
            int selectRoomId = this.getTableSelectedRow(tbl_emp_rooms, 0);

            if (this.reservationManager.getByRoomId(selectRoomId) != null) {
                Helper.showMessage("cannotUpdate");
            } else {
                RoomView roomView = new RoomView(this.roomManager.getRoomsWithDetails(selectRoomId,false).get(0), this.hotelManager.getById(selectHotelId));
                roomView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelTable();
                        loadRoomTable();
                    }
                });
            }
        });

        room_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectRoomId = this.getTableSelectedRow(tbl_emp_rooms, 0);
                if (reservationManager.getByRoomId(selectRoomId) != null) {
                    Helper.showMessage("cannotDelete");
                } else {
                    if (this.roomManager.delete(selectRoomId)) {
                        roomFeatureManager.delete(selectRoomId);
                        Helper.showMessage("done");
                        loadRoomTable();
                        loadReservationTable();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        this.tbl_emp_rooms.setComponentPopupMenu(room_menu);
    }

    private void loadReservationComponent() {
        tableRowSelect(this.tbl_emp_reserv);
        JPopupMenu reserv_menu = new JPopupMenu();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        reserv_menu.add("Güncelle").addActionListener(e -> {
            int selectReservId = this.getTableSelectedRow(tbl_emp_reserv, 0);
            Reservation reservation = this.reservationManager.getById(selectReservId);
            ArrayList<Room> room = this.roomManager.getRoomsWithDetails(reservation.getReservRoomId(),false);
            Hotel hotel = this.hotelManager.getById(reservation.getReservHotelId());
            room.get(0).setHotel(hotel);
            String adultCount = String.valueOf(reservation.getAdultCount());
            String childCount = String.valueOf(reservation.getChildCount());
            String checkInReserve = reservation.getCheckinDate().format(formatter);
            String checkOutReserve = (reservation.getCheckOutDate().format(formatter));

            ReservationView reservationView = new ReservationView(reservation, room.get(0), adultCount, childCount, checkInReserve, checkOutReserve);
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable();

                }
            });
        });

        reserv_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectReservId = this.getTableSelectedRow(tbl_emp_reserv, 0);
                if (this.reservationManager.delete(selectReservId)) {
                    loadRoomTable();
                    loadReservationTable();
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_emp_reserv.setComponentPopupMenu(reserv_menu);
    }
}
