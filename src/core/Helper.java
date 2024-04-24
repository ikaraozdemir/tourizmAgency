package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setTheme() {
        optionPaneTR();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static void showMessage(String str) {
        String msg, title;
        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz !";
                title = "Hata";
                break;
            case "fillSomeAreas":
                msg = "Lütfen giriş-çıkış tarihi ve misafir sayısı alanlarını doldurunuz !";
                title = "Hata";
                break;
            case "done":
                msg = "İşlem başarılı !";
                title = "Sonuç";
                break;
            case "notFound":
                msg = "Kayıt bulunamadı !";
                title = "Bulunamadı";
                break;
            case "error":
                msg = "Hatalı işlem yaptınız !";
                title = "Hata";
                break;
            case "wrong":
                msg = "Kullanıcı adı veya şifre yanlış!";
                title = "Hata";
                break;
            case "cannotDelete":
                msg = "Rezervasyon yapılmış sezonu silemezsiniz !";
                title = "Hata";
                break;
            case "cannotUpdate":
                msg = "Rezervasyon yapılmış, güncelleyemezsiniz !";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;
        if (str.equals("sure")) {
            msg = "Bu işlemi yapmak istediğine emin misin?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,
                msg, "Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static int getLocationPoint(String coordinate, Dimension size) {
        switch (coordinate) {
            case "x":
                return (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y":
                return (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default:
                return 0;
        }
    }

    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }
}
