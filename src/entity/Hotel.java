package entity;

import java.util.ArrayList;

public class Hotel {
    private int hotelId;
    private String city;
    private String region;
    private String hotelName;
    private String hotelPhno;
    private String hotelMail;
    private String star;
    private String hotelAddress;
    private ArrayList<Season> seasons;
    private ArrayList<Pension> pensionTypes;
    private ArrayList<HotelFeature> hotelFeatures;

    public Hotel() {
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelPhno() {
        return hotelPhno;
    }

    public void setHotelPhno(String hotelPhno) {
        this.hotelPhno = hotelPhno;
    }

    public String getHotelMail() {
        return hotelMail;
    }

    public void setHotelMail(String hotelMail) {
        this.hotelMail = hotelMail;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Pension> getPensionTypes() {
        return pensionTypes;
    }

    public void setPensionTypes(ArrayList<Pension> pensionTypes) {
        this.pensionTypes = pensionTypes;
    }

    public ArrayList<HotelFeature> getHotelFeatures() {
        return hotelFeatures;
    }

    public void setHotelFeatures(ArrayList<HotelFeature> hotelFeatures) {
        this.hotelFeatures = hotelFeatures;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelPhno='" + hotelPhno + '\'' +
                ", hotelMail='" + hotelMail + '\'' +
                ", star='" + star + '\'' +
                ", hotelAddress='" + hotelAddress + '\'' +
                ", seasons=" + seasons +
                ", pensionTypes=" + pensionTypes +
                ", hotelFeatures=" + hotelFeatures +
                '}';
    }
}
