package view;

import business.UserManager;
import core.Helper;
import entity.*;

import javax.swing.*;

public class UserView extends Layout {
    private JTextField fld_user_name;
    private JComboBox cmb_user_role;
    private JTextField fld_user_pw;
    private JButton btn_user_save;
    private JPanel container;
    private UserManager userManager;
    private User user;

    public UserView(User user) {

        this.userManager = new UserManager();
        this.add(container);
        this.user = user;
        this.guiInitialize(700, 500);

        this.cmb_user_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));

        if (this.user.getId() != 0) {
            this.fld_user_name.setText(this.user.getName());
            this.fld_user_pw.setText(this.user.getPassword());
            this.cmb_user_role.getModel().setSelectedItem(this.user.getRole());
        }

        btn_user_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_user_name, fld_user_pw})) {
                Helper.showMessage("fill");
            } else {
                boolean result;
                boolean result2 = false;


                this.user.setName(fld_user_name.getText());
                this.user.setPassword(fld_user_pw.getText());
                this.user.setRole((User.Role) this.cmb_user_role.getSelectedItem());

                //kullanıcı güncelleme
                if (this.user.getId() != 0) {
                    result = this.userManager.update(this.user);
                    if (result) {
                        Helper.showMessage("done");

                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                    dispose();
                    //yeni kullanıcı ekleme
                } else {
                    if (this.user.getId() == 0) {
                        result2 = userManager.save(user);
                    }

                    if (result2) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                    dispose();

                }
            }
        });

    }
}
