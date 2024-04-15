package view;

import business.HotelManager;
import business.UserManager;
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
    private Hotel hotel;
    private User user;
    private final DefaultTableModel tmbl_hotels = new DefaultTableModel();
    private final HotelManager hotelManager;
    private Object[] col_hotel;
    private JPopupMenu hotel_menu;

    public EmployeeView (User user) {
        this.hotelManager = new HotelManager();
        this.user = user;
        this.add(container);
        this.guiInitialize(1000,500);
        if (this.user == null) {
            dispose();
        }
        this.lbl_hotel_welcome.setText("Hoşgeldiniz " + this.user.getName());
        loadHotelTable();
        loadHotelComponent();
    }

    public void loadHotelTable() {
        this.col_hotel = new Object[]{"ID", "Şehir", "Bölge", "İsim", "Sabit Telefon", "Mail", "Yıldız Sayısı",
                "Adres", "Pansiyon Özellikleri", "Tesis Özellikleri",  "Sezonlar" };
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.findHotelsWithFeatures());
        createTable(this.tmbl_hotels, this.tbl_emp_hotels, col_hotel, hotelList);
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

       /* this.car_menu.add("Güncelle").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(tbl_car,0);
            CarView carView = new CarView(this.carManager.getById(selectCarId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();

                }
            });
        });
        this.car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectCartId = this.getTableSelectedRow(tbl_car,0);
                if (this.carManager.delete(selectCartId)){
                    Helper.showMsg("done");
                    loadCarTable();
                }else{
                    Helper.showMsg("error");
                }
            }
        });*/
        this.tbl_emp_hotels.setComponentPopupMenu(hotel_menu);


    }




}
