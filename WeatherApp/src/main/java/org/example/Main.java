package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The entry point to the program, builds and displays
 * the gui
 */
public class Main {
    private static final String API_KEY = "074b0008c9908e74560eb4ed254c480c";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    public static JFrame window = null;

    Main() {
        System.out.println("creating the gui");
        System.out.println("event dispatch?" + EventQueue.isDispatchThread());
        window = new JFrame();
        Container windowPane = window.getContentPane();
        windowPane.setLayout(new GridBagLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowPane.setBackground(Color.BLACK);
        window.setSize(1000, 640);
        window.setLocation(100, 100);
        Navigation nav = new Navigation();
        nav.setPreferredSize(new Dimension(100, 550));

        WeatherDetails details = new WeatherDetails();
        details.setPreferredSize(new Dimension(500, 550));

        WeatherFiveDays fiveDays = new WeatherFiveDays();
        fiveDays.setPreferredSize(new Dimension(350, 550));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        //c.ipady = 550;
//        c.ipadx = 60;
        c.insets = new Insets(15, 2, 2, 2);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        windowPane.add(nav, c);
        c.gridx = 1;
        c.gridy = 0;
//        c.ipadx = 550;
        c.weightx = 0.4;
        windowPane.add(details, c);
        c.gridx = 2;
        c.gridy = 0;
//        c.ipadx = 300;
        c.weightx = 0.6;
//        c.insets = new Insets(0, 2, 2, 2);
        windowPane.add(fiveDays, c);
        //window.pack();
    }

    public void display() {
        //parseData("China");
        window.setVisible(true);
    }

    public static String fetchWeatherData(String city) throws Exception {
        String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } else {
            throw new Exception("Failed to fetch weather data");
        }
    }

    private static String getNextDate(String datetimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datetimeString, formatter);
        LocalDate date = dateTime.toLocalDate();
        LocalDate nextDate = date.plusDays(1);
        return nextDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static String getWeekDay(String datetimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datetimeString, formatter);
        LocalDate date = dateTime.toLocalDate();
        return date.getDayOfWeek().toString();
    }

    public static void parseData(String city) {

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            ArrayList<String> daysOftheWeek;
            ArrayList<String> daysImgCodes;
            ArrayList<String> descriptions;
            ArrayList<String> humids;
            WeatherDetailsData details;

            @Override
            protected String doInBackground() throws Exception {
                System.out.println("we are in the worker");
                System.out.println(EventQueue.isDispatchThread());
                String response = fetchWeatherData(city);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //            System.out.println("Weather in " + city + ": " + jsonResponse.toString(2));
                    JSONArray data = jsonResponse.getJSONArray("list");
                    int count = jsonResponse.getInt("cnt");
                    System.out.println(data.getJSONObject(0).toString());
//                    String date = data.getJSONObject(0).getString("dt_txt");
                    double rain = data.getJSONObject(0).getDouble("pop");
                    JSONArray weatherData = data.getJSONObject(0).getJSONArray("weather");
                    JSONObject mainData = data.getJSONObject(0).getJSONObject("main");
                    double temp = mainData.getDouble("temp");
                    String imgCode = weatherData.getJSONObject(0).getString("icon");
                    Double feel = mainData.getDouble("feels_like");
                    Double wind = data.getJSONObject(0).getJSONObject("wind").getDouble("speed");
                    int humidity = mainData.getInt("humidity");

                    ArrayList<String> days = new ArrayList<>();
                    ArrayList<String> codes = new ArrayList<>();
                    ArrayList<String> tempps = new ArrayList<>();
                    ArrayList<String> conds = new ArrayList<>();

                    conds.add(feel + "\u00B0");
                    conds.add(wind + "km/h");
                    conds.add(rain * 100 + "%");
                    conds.add(humidity + "%");


                    for (int i = 0; i < 6; i++) {
                        String dd = data.getJSONObject(i).getString("dt_txt");
                        String code = data.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                        String tempp = data.getJSONObject(i).getJSONObject("main").getDouble("temp") + "";

                        days.add(dd);
                        codes.add(code);
                        tempps.add(tempp);
                    }


                    daysOftheWeek = new ArrayList<>();
                    daysImgCodes = new ArrayList<>();
                    descriptions = new ArrayList<>();
                    humids = new ArrayList<>();


                    String dd = data.getJSONObject(0).getString("dt_txt");
                    daysOftheWeek.add(getWeekDay(dd));
                    daysImgCodes.add(imgCode);
                    descriptions.add(weatherData.getJSONObject(0).getString("main"));
                    humids.add(humidity + "%");
                    int cc = 0;
                    for (int i = 1; i < count && cc < 6; i++) {
                        String ddInData = data.getJSONObject(i).getString("dt_txt");
                        if (ddInData.startsWith(getNextDate(dd))) {
                            JSONObject nextData = data.getJSONObject(i);
                            String nextDD = getWeekDay(nextData.getString("dt_txt"));
                            daysOftheWeek.add(nextDD);
                            String imgIc = nextData.getJSONArray("weather").getJSONObject(0).getString("icon");
                            daysImgCodes.add(imgIc);
                            String des = nextData.getJSONArray("weather").getJSONObject(0).getString("main");
                            descriptions.add(des);
                            String hum = nextData.getJSONObject("main").getInt("humidity") + "%";
                            humids.add(hum);

                            dd = ddInData;
                            cc++;
                        }

                    }
                    //window.validate();
                    details = new WeatherDetailsData(city, rain * 100 + "", temp + "", imgCode, days, tempps, codes, conds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "done";
            }
            @Override
            protected void done() {
                window.invalidate();
                WeatherDetails.updateWeatherDetails(details);
                WeatherFiveDays.updateFiveData(daysOftheWeek, daysImgCodes, descriptions, humids);
                window.revalidate();
                window.repaint();
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
            System.out.println(EventQueue.isDispatchThread());
            Main.parseData("china");
            //Thread.sleep(50000);
            gui.display();
        });
    }

}

