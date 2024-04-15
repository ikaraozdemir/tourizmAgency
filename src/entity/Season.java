package entity;

import java.time.LocalDate;

public class Season {
    private int seasonId;
    private int seasonHotelId;
    private LocalDate strtDate;
    private LocalDate endDate;
    private String seasonName;

    public Season(int seasonId, int seasonHotelId, LocalDate strtDate, LocalDate fnshDate, String seasonName) {
        this.seasonId = seasonId;
        this.seasonHotelId = seasonHotelId;
        this.strtDate = strtDate;
        this.endDate = fnshDate;
        this.seasonName = seasonName;
    }

    public Season() {
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getSeasonHotelId() {
        return seasonHotelId;
    }

    public void setSeasonHotelId(int seasonHotelId) {
        this.seasonHotelId = seasonHotelId;
    }

    public LocalDate getStrtDate() {
        return strtDate;
    }

    public void setStrtDate(LocalDate strtDate) {
        this.strtDate = strtDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
