package entity;

public class Pension {

    private int pensionId;
    private int pensionHotelId;
    private String pensionType;


    public Pension() {
    }


    public int getPensionId() {
        return pensionId;
    }

    public void setPensionId(int pensionId) {
        this.pensionId = pensionId;
    }

    public int getPensionHotelId() {
        return pensionHotelId;
    }

    public void setPensionHotelId(int pensionHotelId) {
        this.pensionHotelId = pensionHotelId;
    }

    public String getPensionType() {
        return pensionType;
    }

    public void setPensionType(String pensionType) {
        this.pensionType = pensionType;
    }
}
