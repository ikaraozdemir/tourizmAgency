package view;
import business.UserManager;
import core.Helper;
import entity.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout{
    private JPanel container;
    private JButton btn_user_logout;
    private JTabbedPane tabbedPane1;
    private JTextField fld_user_role;
    private JButton btn_user_clear;
    private JButton btn_user_search;
    private JTable tbl_users;
    private JLabel lbl_welcome;
    private User user;
    private final DefaultTableModel tmbl_users = new DefaultTableModel();
    private final UserManager userManager;
    private Object[] col_user;

    public AdminView (User user) {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitialize(700,500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getName());
        loadUserTable();
//        loadUserComponent();
    }

    public void loadUserTable() {
        this.col_user = new Object[]{"ID", "Rol", "İsim Soyisim", "Şifre"};
        ArrayList<Object[]> userList = this.userManager.getForTable(col_user.length, this.userManager.findAll());
        createTable(this.tmbl_users, this.tbl_users, col_user, userList);
    }

//    private void loadUserComponent() {
//        tableRowSelect(this.tbl_users);
//
//        this.car_menu = new JPopupMenu();
//        this.car_menu.add("Yeni").addActionListener(e -> {
//            CarView carView = new CarView(new Car());
//            carView.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    loadCarTable();
//
//                }
//            });
//        });
//
//        this.car_menu.add("Güncelle").addActionListener(e -> {
//            int selectCarId = this.getTableSelectedRow(tbl_car,0);
//            CarView carView = new CarView(this.carManager.getById(selectCarId));
//            carView.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    loadCarTable();
//
//                }
//            });
//        });
//        this.car_menu.add("Sil").addActionListener(e -> {
//            if (Helper.confirm("sure")){
//                int selectCartId = this.getTableSelectedRow(tbl_car,0);
//                if (this.carManager.delete(selectCartId)){
//                    Helper.showMsg("done");
//                    loadCarTable();
//                }else{
//                    Helper.showMsg("error");
//                }
//            }
//        });
//        this.tbl_car.setComponentPopupMenu(car_menu);
//    }


}
