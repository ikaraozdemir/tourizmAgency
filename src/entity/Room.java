package entity;

import java.util.ArrayList;

public class Room {
    private int roomId;
    private int roomHotelId;
    private int roomSeasonId;
    private int roomPensionId;
    private int roomStock;
    private int priceAdult;
    private int priceChild;
    private Type type;
    private Season season;
    private Hotel hotel;
    private Pension pension;
    private ArrayList<RoomFeature> roomFeatures;

    public Room() {
    }

    public enum Type {
        SINGLE,
        DOUBLE,
        JUNIOR_SUITE,
        SUITE
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomHotelId() {
        return roomHotelId;
    }

    public void setRoomHotelId(int roomHotelId) {
        this.roomHotelId = roomHotelId;
    }

    public int getRoomSeasonId() {
        return roomSeasonId;
    }

    public void setRoomSeasonId(int roomSeasonId) {
        this.roomSeasonId = roomSeasonId;
    }

    public int getRoomPensionId() {
        return roomPensionId;
    }

    public void setRoomPensionId(int roomPensionId) {
        this.roomPensionId = roomPensionId;
    }

    public int getRoomStock() {
        return roomStock;
    }

    public void setRoomStock(int roomStock) {
        this.roomStock = roomStock;
    }

    public int getPriceAdult() {
        return priceAdult;
    }

    public void setPriceAdult(int priceAdult) {
        this.priceAdult = priceAdult;
    }

    public int getPriceChild() {
        return priceChild;
    }

    public void setPriceChild(int priceChild) {
        this.priceChild = priceChild;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<RoomFeature> getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(ArrayList<RoomFeature> roomFeatures) {
        this.roomFeatures = roomFeatures;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomHotelId=" + roomHotelId +
                ", roomSeasonId=" + roomSeasonId +
                ", roomPensionId=" + roomPensionId +
                ", roomStock=" + roomStock +
                ", priceAdult=" + priceAdult +
                ", priceChild=" + priceChild +
                ", type=" + type +
                ", season=" + season +
                ", hotel=" + hotel +
                ", pension=" + pension +
                ", roomFeature=" + roomFeatures +
                '}';
    }
}
