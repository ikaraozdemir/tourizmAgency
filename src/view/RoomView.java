package view;

import business.PensionManager;
import business.RoomFeatureManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RoomView extends Layout {
    private JPanel container;
    private JComboBox cmb_room_type;
    private JComboBox cmb_pension_type;
    private JComboBox cmb_season_name;
    private JTextField fld_room_stock;
    private JTextField fld_room_adult_prc;
    private JTextField fld_room_child_prc;
    private JCheckBox tv_cb;
    private JCheckBox kasa_cb;
    private JCheckBox minibar_cb;
    private JCheckBox projeksiyon_cb;
    private JCheckBox konsol_cb;
    private JTextField fld_room_feature_size;
    private JTextField fld_room_feature_beds;
    private JButton btn_room_save;
    private JLabel lbl_room_size;
    private JLabel lbl_beds;
    private RoomManager roomManager;
    private PensionManager pensionManager;
    private SeasonManager seasonManager;
    private RoomFeatureManager roomFeatureManager;
    private final Room room;
    private final Hotel hotel;
    private final ArrayList<JCheckBox> cbRoomFeatures = new ArrayList<>();


    public RoomView(Room room, Hotel hotel){
        this.roomManager = new RoomManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.roomFeatureManager = new RoomFeatureManager();
        this.add(container);
        this.guiInitialize(700,700);
        this.room = room;
        this.hotel = hotel;

//        for (Brand brand : this.brandManager.findAll()) {
//            this.cmb_brand.addItem(new ComboItem(brand.getId(), brand.getName()));
//        }

        this.cmb_room_type.setModel(new DefaultComboBoxModel<>(Room.Type.values()));

        ArrayList<Pension> pensions = pensionManager.getPensionsByHotelId(this.hotel.getHotelId());

        for (Pension pension : pensions) {
            this.cmb_pension_type.addItem(pension.getPensionType());

        }

        ArrayList<Season> seasons = seasonManager.getSeasonsByHotelId(this.hotel.getHotelId());
        for (Season season : seasons) {
            this.cmb_season_name.addItem(season.getSeasonName());

        }


        cbRoomFeatures.addAll(Arrays.asList(tv_cb,kasa_cb,minibar_cb,projeksiyon_cb,konsol_cb));

        btn_room_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_room_adult_prc, fld_room_child_prc, fld_room_stock, fld_room_feature_size, fld_room_feature_beds})) {
                Helper.showMessage("fill");
            } else {
                boolean result2 = false;
                this.room.setHotel(this.hotel);
                this.room.setRoomHotelId(this.hotel.getHotelId());
                System.out.println(this.room.getRoomHotelId() + " numaralı otel");
                this.room.setRoomStock(Integer.parseInt(fld_room_stock.getText()));
                this.room.setPriceAdult(Integer.parseInt(fld_room_adult_prc.getText()));
                this.room.setPriceChild(Integer.parseInt(fld_room_child_prc.getText()));

                ArrayList<RoomFeature> selectedFeatures = new ArrayList<>();


                RoomFeature featureFromTextBox1 = new RoomFeature();
                featureFromTextBox1.addRoomFeature(lbl_room_size.getText(),fld_room_feature_size.getText());
                selectedFeatures.add(featureFromTextBox1);

                RoomFeature featureFromTextBox2 = new RoomFeature();
                featureFromTextBox2.addRoomFeature(lbl_beds.getText(),fld_room_feature_beds.getText());
                selectedFeatures.add(featureFromTextBox2);

                for (JCheckBox checkBox : cbRoomFeatures) {
                    if (checkBox.isSelected()) {
                        RoomFeature feature = new RoomFeature();
                        feature.addRoomFeature(checkBox.getText(),true);
                        feature.setRoomFeatureRoomId(this.room.getRoomId());
                        selectedFeatures.add(feature);
                    }
                }
                this.room.setRoomFeatures(selectedFeatures);
                this.room.setType((Room.Type) this.cmb_room_type.getSelectedItem());

                for (Pension pension: pensions){
                    if (pension.getPensionType().equals(cmb_pension_type.getSelectedItem())){
                        this.room.setPension(pension);
                    }
                }

                for (Season season: seasons){
                    if (season.getSeasonName().equals(cmb_season_name.getSelectedItem())){
                        this.room.setSeason(season);
                    }
                }
                if (this.room.getRoomId() != 0){
                    dispose();
                } else {
                    int roomId = this.roomManager.saveAndGetRoomId(this.room);
                    if (roomId != 0) {
                        for (RoomFeature feature : selectedFeatures) {
                            feature.setRoomFeatureRoomId(roomId);
                            result2 = this.roomFeatureManager.save(feature);
                        }
                    }
                    dispose();

                    if (roomId != 0 && result2) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

    }

}
