package comb.example.tito.weatherapp.Model;

/**
 * Created by Tito on 16/10/2017.
 */

public class Sys {
    private double mewssage;
    private String country;
    private double sunrise;
    private double sunset;

    public Sys(double mewssage, String country, double sunrise, double sunset) {
        this.mewssage = mewssage;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public double getMewssage() {
        return mewssage;
    }

    public void setMewssage(double mewssage) {
        this.mewssage = mewssage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }
}
