package org.example;

import java.util.ArrayList;

/**
 * Models the data used to populate the WeatherDetails section
 * of the GUI
 */
public class WeatherDetailsData {
    public final String city, rain, temp, code;
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> imgCodes = new ArrayList<>();
    ArrayList<String> temps = new ArrayList<>();
    ArrayList<String> conditions = new ArrayList<>();

    public WeatherDetailsData(String city, String rain, String temp, String code,
                              ArrayList<String> times, ArrayList<String> temps,
                              ArrayList<String> imgCodes, ArrayList<String> cond) {
        this.city = city;
        this.rain = rain;
        this.temp = temp;
        this.code = code;
        this.times.addAll(times);
        this.temps.addAll(temps);
        this.imgCodes.addAll(imgCodes);
        this.conditions.addAll(cond);

    }
}
