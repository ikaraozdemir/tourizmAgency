package entity;

public class HotelFeature {

    private int hotelFeatureId;
    private int hotelFeatureHotelId;
    private String hotelFeature;


    public HotelFeature() {
    }

    public int getHotelFeatureId() {
        return hotelFeatureId;
    }

    public void setHotelFeatureId(int hotelFeatureId) {
        this.hotelFeatureId = hotelFeatureId;
    }

    public int getHotelFeatureHotelId() {
        return hotelFeatureHotelId;
    }

    public void setHotelFeatureHotelId(int hotelFeatureHotelId) {
        this.hotelFeatureHotelId = hotelFeatureHotelId;
    }

    public String getHotelFeature() {
        return hotelFeature;
    }

    public void setHotelFeature(String hotelFeature) {
        this.hotelFeature = hotelFeature;
    }
}
