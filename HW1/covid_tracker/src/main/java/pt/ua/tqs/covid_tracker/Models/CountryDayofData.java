package pt.ua.tqs.covid_tracker.Models;

public class CountryDayofData {

    private String country;
    private int dayOfData;

    public CountryDayofData(String country, int dayOfData){
        this.country = country;
        this.dayOfData = dayOfData;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDayOfData() {
        return this.dayOfData;
    }

    public void setDayOfData(int dayOfData) {
        this.dayOfData = dayOfData;
    }

}
