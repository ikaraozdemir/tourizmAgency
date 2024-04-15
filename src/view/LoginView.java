package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {

    private JTextField fld_username;
    private JButton btn_login;
    private JPanel container;
    private JPasswordField fld_pw;
    private final UserManager userManager;

    public LoginView () {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitialize(400, 400);
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pw};
            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMessage("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pw.getText());
                if (loginUser == null) {
                    Helper.showMessage("notFound");
                } else if (loginUser.getRole().equals("admin")) {
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }else if (loginUser.getRole().equals("employee")) {
                    EmployeeView employeeView = new EmployeeView(loginUser);
                    dispose();
                }
            }
        });
    }
}
