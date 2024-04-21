package entity;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int reservId;
    private int reservRoomId;
    private int reservHotelId;
    private int adultCount;
    private int childCount;
    private String guestIdno;
    private String guestName;
    private String guestMpno;
    private String guestMail;
    private int totalPrice;
    private String reservNote;
    private int totalDayCount;
    private LocalDate checkinDate;
    private LocalDate checkOutDate;
    private Room room;
    private Hotel hotel;

    public Reservation() {
    }

    public int getReservId() {
        return reservId;
    }

    public void setReservId(int reservId) {
        this.reservId = reservId;
    }

    public int getReservRoomId() {
        return reservRoomId;
    }

    public void setReservRoomId(int reservRoomId) {
        this.reservRoomId = reservRoomId;
    }

    public int getReservHotelId() {
        return reservHotelId;
    }

    public void setReservHotelId(int reservHotelId) {
        this.reservHotelId = reservHotelId;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public String getGuestIdno() {
        return guestIdno;
    }

    public void setGuestIdno(String guestIdno) {
        this.guestIdno = guestIdno;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestMpno() {
        return guestMpno;
    }

    public void setGuestMpno(String guestMpno) {
        this.guestMpno = guestMpno;
    }

    public String getGuestMail() {
        return guestMail;
    }

    public void setGuestMail(String guestMail) {
        this.guestMail = guestMail;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReservNote() {
        return reservNote;
    }

    public void setReservNote(String reservNote) {
        this.reservNote = reservNote;
    }

    public int getTotalDayCount() {
        return totalDayCount;
    }

    public void setTotalDayCount(int totalDayCount) {
        this.totalDayCount = totalDayCount;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservId=" + reservId +
                ", reservRoomId=" + reservRoomId +
                ", reservHotelId=" + reservHotelId +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                ", guestIdno='" + guestIdno + '\'' +
                ", guestName='" + guestName + '\'' +
                ", guestMpno='" + guestMpno + '\'' +
                ", guestMail='" + guestMail + '\'' +
                ", totalPrice=" + totalPrice +
                ", reservNote='" + reservNote + '\'' +
                ", totalDayCount=" + totalDayCount +
                ", checkinDate=" + checkinDate +
                ", checkOutDate=" + checkOutDate +
                ", room=" + room +
                ", hotel=" + hotel +
                '}';
    }
}
