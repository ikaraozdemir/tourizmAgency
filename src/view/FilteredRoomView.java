package view;

import business.RoomManager;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FilteredRoomView extends Layout{
    private JPanel container;
    private JTable tbl_searched_room;
    private JScrollPane scrol;
    private Object[] col_searched_room;
    private final DefaultTableModel tmbl_searched_room = new DefaultTableModel();
    private JPopupMenu searched_room_menu;
    private RoomManager roomManager;
    private String adult;
    private String child;
    private String checkIn;
    private String checkOut;

    public FilteredRoomView (String adult, String child, String checkIn, String checkOut) {
        this.add(container);
        this.guiInitialize(700, 700);
        this.roomManager = new RoomManager();
        this.adult = adult;
        this.child = child;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void loadSearchedRoomTable(ArrayList<Object[]> roomReservationRow) {
        this.col_searched_room = new Object[]{"ID", "Otel","Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
                "Oda Stoğu", "Oda Tipi", "Oda Özellikleri", "Toplam Gün", "Yetişkin İçin Fiyat", "Çocuk İçin Fiyat", "Toplam Fiyat"};
        createTable(this.tmbl_searched_room, this.tbl_searched_room, col_searched_room, roomReservationRow);
    }

    public void loadSearchedRoomComponent(ArrayList<Room> rooms) {
        tableRowSelect(this.tbl_searched_room);
        this.searched_room_menu = new JPopupMenu();
        this.searched_room_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectSeearchedRoomId = this.getTableSelectedRow(tbl_searched_room,0);
            ReservationView reservationView = null;
            Reservation reservation = new Reservation();

            for (Room room : rooms) {
                if (room.getRoomId() == selectSeearchedRoomId) {
                    reservationView = new ReservationView(reservation,room, adult, child, checkIn, checkOut);

                }
            }
            ///////////////////////////////
            if (reservationView != null) {
                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        EmployeeView employeeView = new EmployeeView();
                        employeeView.loadReservationTable();
                    }
                });
            }
        });
        this.tbl_searched_room.setComponentPopupMenu(searched_room_menu);


    }



}
