package view;
/// string kırpma burada
import business.HotelManager;
import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReservationView extends Layout {
    private JPanel container;
    private JTextField fld_reserv_idno;
    private JTextField fld_reserv_name;
    private JButton btn_reserv_save;
    private JTextField fld_reserv_mpno;
    private JTextField fld_reserv_mail;
    private JTextArea txt_reserv_note;
    private JLabel lbl_reserv_tc;
    private JLabel lbl_reserv_total_prc;
    private JLabel lbl_resrv_child;
    private JLabel lbl_reserv_adult;
    private JLabel lbl_checkin;
    private JLabel lbl_checkout;
    private JLabel lbl_reserv_room_features;
    private JLabel lbl_reserv_room;
    private JLabel lbl_reserv_season;
    private JLabel lbl_reserv_hotel_features;
    private JLabel lbl_reserv_hotel_name;
    private Room room;
    private Reservation reservation;
    private HotelManager hotelManager;
    private ReservationManager reservationManager;
    private String adult;
    private String child;
    private String checkIn;
    private String checkOut;


    public ReservationView(Reservation reservation, Room room, String adult,String child, String checkIn, String checkOut) {
        this.add(container);
        this.guiInitialize(700, 700);
        this.reservationManager = new ReservationManager();
        this.room = room;
        this.adult =adult;
        this.child = child;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservation = reservation;


        this.lbl_reserv_hotel_name.setText("Otel: " + room.getHotel().getHotelName());
        this.lbl_reserv_room.setText("Oda Tipi: " + room.getPension().getPensionType());
        this.lbl_resrv_child.setText("Çocuk Sayısı: " + child);
        this.lbl_reserv_adult.setText("Yetişkin Sayısı: " + adult);
        this.lbl_checkout.setText("Check-out: " + this.checkOut);
        this.lbl_checkin.setText("Check-in: " + this.checkIn);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateIn = LocalDate.parse(checkIn, formatter);
        LocalDate dateOut = LocalDate.parse(checkOut, formatter);
        int totalDays =  (int) ChronoUnit.DAYS.between(dateIn, dateOut);
        int totalChildAdultPrice = Integer.parseInt(adult)  * room.getPriceAdult() + Integer.parseInt(child)  * room.getPriceChild();
        int totalPrice =  totalChildAdultPrice * totalDays;

        this.lbl_reserv_total_prc.setText("Toplam Tutar: " + totalPrice);
        this.lbl_reserv_season.setText("Sezon: " + this.room.getSeason().getStrtDate() + " " + this.room.getSeason().getEndDate());

        StringBuilder roomFeatures = new StringBuilder();
        for(RoomFeature roomFeature: this.room.getRoomFeatures()) {
            String keySet = roomFeature.getRoomFeature().keySet().toString();
            String values = roomFeature.getRoomFeature().values().toString();
            String keySetUpt =  keySet.substring(2, keySet.length() - 2);
            String valuesUpt =  values.substring(2, values.length() - 2);
            roomFeatures.append(keySetUpt).append(" ");
            if (keySetUpt.equals("Oda Boyutu (metrekare):")) {
                roomFeatures.append(valuesUpt).append(", ");
            } else if (keySetUpt.equals("Yatak Sayısı:")) {
                roomFeatures.append(valuesUpt).append(", ");
            } else {
                roomFeatures.append(", ");
            }
        }
        if (roomFeatures.toString().endsWith(", ")) {
            roomFeatures = new StringBuilder(roomFeatures.substring(0, roomFeatures.length() - 2));
        }
        System.out.println(roomFeatures);
        this.lbl_reserv_room_features.setText("Oda Özellikleri: " + roomFeatures);

        StringBuilder hotelFeatures = new StringBuilder();
        for(HotelFeature hotelFeature: this.room.getHotel().getHotelFeatures()) {
            String feature = hotelFeature.getHotelFeature();
            hotelFeatures.append(feature).append(", ");
        }
        if (hotelFeatures.toString().endsWith(", ")) {
            hotelFeatures = new StringBuilder(hotelFeatures.substring(0, hotelFeatures.length() - 2));
        }
        this.lbl_reserv_hotel_features.setText("Tesis Özellikleri: " + hotelFeatures);

        if (this.reservation.getReservId() != 0) {
            this.fld_reserv_name.setText(this.reservation.getGuestName());
            this.fld_reserv_idno.setText(this.reservation.getGuestIdno());
            this.fld_reserv_mpno.setText(this.reservation.getGuestMpno());
            this.fld_reserv_mail.setText(this.reservation.getGuestMail());
            this.txt_reserv_note.setText(this.reservation.getReservNote());
        }


        this.btn_reserv_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_reserv_idno, fld_reserv_name,
                    fld_reserv_mail, fld_reserv_mpno})) {
                Helper.showMessage("fill");
            } else {
                boolean result = false;
                boolean result2 = false;
                this.reservation.setReservRoomId(this.room.getRoomId());
                this.reservation.setReservHotelId(this.room.getRoomHotelId());
                this.reservation.setHotel(this.room.getHotel());
                this.reservation.setChildCount(Integer.parseInt(child));
                this.reservation.setAdultCount(Integer.parseInt(adult));
                this.reservation.setGuestIdno(this.fld_reserv_idno.getText());
                this.reservation.setGuestName(this.fld_reserv_name.getText());
                this.reservation.setGuestMail(this.fld_reserv_mail.getText());
                this.reservation.setGuestMpno(this.fld_reserv_mpno.getText());
                this.reservation.setTotalPrice(totalPrice);
                this.reservation.setReservNote(this.txt_reserv_note.getText());
                this.reservation.setTotalDayCount(totalDays);
                this.reservation.setCheckinDate(dateIn);
                this.reservation.setCheckOutDate(dateOut);
                this.reservation.setRoom(this.room);
                this.reservation.setHotel(this.room.getHotel());

                //güncelle
                if (this.reservation.getReservId() != 0) {
                    result = this.reservationManager.update(reservation);

                    if (result) {
                        Helper.showMessage("done");
                        dispose();
                    } else {
                        Helper.showMessage("error");
                    }

                dispose();
                }  else {
                    if (this.reservation.getReservId() == 0) {
                        result2 = this.reservationManager.save(this.reservation);
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