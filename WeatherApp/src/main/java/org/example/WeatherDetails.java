package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Defines the WeatherDetails section of the GUI
 */
public class WeatherDetails extends RoundedPanel {
    private static CustomLabel city;
    private static CustomLabel rain;
    private static CustomLabel temp;
    private static JLabel wPic;
    private static final ArrayList<JLabel> foreCastImages = new ArrayList<>();
    private static final ArrayList<CustomLabel> foreCastTemps = new ArrayList<>();
    private static final ArrayList<CustomLabel> foreCastTimes = new ArrayList<>();
    private static final ArrayList<CustomLabel> conditionMetrics = new ArrayList<>();
    private static JPanel container = null;

    public WeatherDetails() {
        super(500, 550, Color.BLACK);
        container = this;
        setLayout(new GridBagLayout());
        RoundedPanel searchBar = new RoundedPanel(450, 40, new Color(20, 30, 40));
        searchBar.setLayout(new GridBagLayout());
        // add input to searchbar
        JTextField in = new JTextField("Search cities");
        in.setPreferredSize(new Dimension(400, 38));
        in.setBackground(new Color(20, 30, 40));
        in.setForeground(Color.WHITE);
        in.setBorder(null);
        in.addActionListener(e -> {
            System.out.println("were in the event listener");
            System.out.println(EventQueue.isDispatchThread());
            Main.parseData(in.getText());
            Main.window.repaint();

        });

        searchBar.add(in);
        searchBar.setPreferredSize(new Dimension(450, 40));

        // populate main details
        RoundedPanel mainDetails = new RoundedPanel(450, 150, Color.black);
        mainDetails.setPreferredSize(new Dimension(450, 150));

        // // add labels to leftDetails
        RoundedPanel leftDetails = new RoundedPanel(150, 145, Color.black);
        leftDetails.setPreferredSize(new Dimension(150, 145));
        leftDetails.setLayout(new GridBagLayout());
        city = new CustomLabel("Madrid");
        rain = new CustomLabel("Chance of rain: 0%");
        rain.setForeground(new Color(150, 150, 150));
        temp = new CustomLabel("31\u00B0");
        city.setPreferredSize(new Dimension(300, 30));
        city.setFont(new Font("Roboto", Font.BOLD, 30));
        rain.setPreferredSize(new Dimension(140, 20));
        temp.setPreferredSize(new Dimension(140, 40));
        temp.setFont(new Font("Roboto", Font.BOLD, 40));
        GridBagConstraints cLeftDetails = new GridBagConstraints();
        cLeftDetails.anchor = GridBagConstraints.PAGE_START;
        cLeftDetails.insets = new Insets(8, 2, 2, 2);
        cLeftDetails.gridx = 0;
        cLeftDetails.gridy = 0;
        cLeftDetails.weighty = 0;
        leftDetails.add(city, cLeftDetails);
        cLeftDetails.weighty = 0.1;
        cLeftDetails.gridy = 1;
        leftDetails.add(rain, cLeftDetails);
        cLeftDetails.gridy = 2;
        cLeftDetails.weighty = 0.2;
        leftDetails.add(temp, cLeftDetails);

        // set right details
        RoundedPanel rightDetails = new RoundedPanel(150, 145, Color.black);
        rightDetails.setPreferredSize(new Dimension(150, 145));
        wPic = new JLabel();
        wPic.setIcon(new ImageIcon(Objects.requireNonNull(getWeatherImage(true))));
        //wPic.setPreferredSize(new Dimension());
        rightDetails.add(wPic);

        mainDetails.setLayout(new BorderLayout());
        mainDetails.add(leftDetails, BorderLayout.WEST);
        mainDetails.add(rightDetails, BorderLayout.EAST);


        // populate forecast
        RoundedPanel forecast = new RoundedPanel(450, 150, new Color(20, 30, 40));
        forecast.setPreferredSize(new Dimension(450, 150));
        forecast.setLayout(new GridBagLayout());
        CustomLabel forecastLabel = new CustomLabel("TODAY'S FORECAST");
        forecastLabel.setForeground(new Color(150, 150, 150));


        RoundedPanel forecastData = new RoundedPanel(440, 120, new Color(20, 30, 40));
        forecastData.setPreferredSize(new Dimension(440, 120));
        forecastData.setLayout(new GridLayout(1, 6));
        String timeString = "6:00 AM";
        for (int i = 0; i < 6; i++) {
            RoundedPanel data1 = new RoundedPanel(72, 120, new Color(20, 30, 40));
            data1.setPreferredSize(new Dimension(72, 120));

            if (i > 0) {
                data1.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(150, 150, 150)));
            }
            data1.setLayout(new GridLayout(3, 1));
            CustomLabel time = new CustomLabel(timeString, SwingConstants.CENTER);
            CustomLabel foreTemp = new CustomLabel("25\u00B0", SwingConstants.CENTER);
            foreCastTemps.add(foreTemp);
            foreCastTimes.add(time);
            foreTemp.setForeground(Color.WHITE);
            foreTemp.setFont(new Font("Roboto", Font.BOLD, 20));
            time.setForeground(new Color(150, 150, 150));
            JLabel forePic = new JLabel(new ImageIcon(Objects.requireNonNull(getWeatherImage(false))), SwingConstants.CENTER);
            foreCastImages.add(forePic);
            data1.add(time);
            data1.add(forePic);
            data1.add(foreTemp);
            forecastData.add(data1);
            if (i == 0) timeString = "9:00 AM";
            else if (i == 1) timeString = "12: 00 PM";
            else if (i == 2) timeString = "3: 00 PM";
            else if (i == 3) timeString = "6: 00 PM";
            else if (i == 4) timeString = "9: 00 pM";
        }

        GridBagConstraints cForecast = new GridBagConstraints();
        cForecast.anchor = GridBagConstraints.FIRST_LINE_START;
        cForecast.gridx = 0;
        cForecast.gridy = 0;
        cForecast.weighty = 0.2;
        cForecast.insets = new Insets(2, 2, 2, 2);
        forecast.add(forecastLabel, cForecast);
        cForecast.gridy = 1;
        cForecast.weighty = 0.4;
        forecast.add(forecastData, cForecast);

        RoundedPanel conditions = new RoundedPanel(450, 150, new Color(20, 30, 40));
        conditions.setPreferredSize(new Dimension(450, 150));
        conditions.setLayout(new GridBagLayout());

        CustomLabel conditionsLabel = new CustomLabel("AIR CONDITIONS");
        conditionsLabel.setForeground(new Color(150, 150, 150));
        RoundedPanel conditionsDetails = new RoundedPanel(440, 120, new Color(20, 30, 40));

        conditionsDetails.setPreferredSize(new Dimension(440, 120));

        conditionsDetails.setLayout(new GridLayout(2, 2));


//        RoundedPanel wind = new RoundedPanel(220, 58, Color.RED);
//        RoundedPanel rainChance = new RoundedPanel(220, 58, Color.PINK);
//        RoundedPanel uv = new RoundedPanel(220, 58, Color.CYAN);

        String conditionLabel = "Real Feel";
        for (int i = 0; i < 4; i++) {
            RoundedPanel feel = new RoundedPanel(220, 56, new Color(20, 30, 40));
            feel.setBorder(new EmptyBorder(0, 2, 4, 0));
            CustomLabel feelTitle = new CustomLabel(conditionLabel);
            feelTitle.setFont(new Font("Roboto", Font.BOLD, 14));
            feelTitle.setForeground(new Color(150, 150, 150));
            CustomLabel feelTemp = new CustomLabel("30\u00B0");
            conditionMetrics.add(feelTemp);
            feelTemp.setFont(new Font("Roboto", Font.BOLD, 25));
            feel.setLayout(new BorderLayout());
            feel.add(feelTitle, BorderLayout.NORTH);
            feel.add(feelTemp, BorderLayout.SOUTH);
            if (i == 0) conditionLabel = "Wind";
            else if (i == 1) conditionLabel = "Chance of rain";
            else if (i == 2) conditionLabel = "Humidity";
            conditionsDetails.add(feel);
        }


//        conditionsDetails.add(wind);
//        conditionsDetails.add(rainChance);
//        conditionsDetails.add(uv);

        GridBagConstraints cConditions = new GridBagConstraints();
        cConditions.anchor = GridBagConstraints.FIRST_LINE_START;
        cConditions.insets = new Insets(4, 2, 2, 2);
        cConditions.gridx = 0;
        cConditions.gridy = 0;
        cConditions.weighty = 0;
        conditions.add(conditionsLabel, cConditions);
        cConditions.weighty = 0.1;
        cConditions.gridy = 1;
        conditions.add(conditionsDetails, cConditions);


        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        //c.weightx =;
        //c.gridheight = 4;
        c.weighty = 0;
        //c.ipadx = 400;
        //c.fill = GridBagConstraints.HORIZONTAL;
        add(searchBar, c);
        c.gridy = 1;
        c.weighty = 0.1;
        add(mainDetails, c);
        //c.weighty = 0.1;
        c.gridy = 2;
        c.weighty = 0.2;
        c.insets = new Insets(1, 1, 1, 1);
        //c.anchor = GridBagConstraints.CENTER;
        add(forecast, c);
        c.gridy = 3;
        c.weighty = 0.4;
        add(conditions, c);
    }

    public static Image getWeatherImage(boolean scale) {
        Image image;
        Image iconImage;
        String imgUrl = "http://openweathermap.org/img/w/10d.png";
        if (scale) imgUrl = "http://openweathermap.org/img/wn/10d@2x.png";
        try {
            URL url = new URL(imgUrl);
            image = ImageIO.read(url);
            iconImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            if (scale) return iconImage;
            return image;
        } catch (IOException e) {
            System.out.println("An error occurred");
            return null;
        }
    }

    public static Image getWeatherImage(String code, boolean scale) {
        Image image;
        Image iconImage;
        String imgUrl = "http://openweathermap.org/img/w/" + code + ".png";
        if (scale) imgUrl = "http://openweathermap.org/img/wn/" + code + "@2x.png";
        try {
            URL url = new URL(imgUrl);
            image = ImageIO.read(url);
            iconImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            if (scale) return iconImage;
            return image;
        } catch (IOException e) {
            System.out.println("An error occurred");
            return null;
        }
    }

    private static String formatDate(String datetimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datetimeString, formatter);
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        if (hour >= 12) {
            if (hour > 12) hour = hour - 12;
            return String.format("%d:%02d PM", hour, minute);
        }

        return String.format("%d:%02d AM", hour, minute);
    }


    public static void updateWeatherDetails(org.example.WeatherDetailsData data) {
        city.setText(data.city);
        rain.setText("Chance of rain: " + data.rain + "%");
        temp.setText(data.temp + "\u00B0");
        wPic.setIcon(new ImageIcon(
                Objects.requireNonNull(getWeatherImage(data.code, true))));
        for (int i = 0; i < data.imgCodes.size(); i++) {
            foreCastTimes.get(i).setText(formatDate(data.times.get(i)));
            foreCastTemps.get(i).setText(data.temps.get(i));
            foreCastImages.get(i).setIcon(new ImageIcon(
                    Objects.requireNonNull(getWeatherImage(data.imgCodes.get(i), false))));
        }

        for (int i = 0; i < conditionMetrics.size(); i++) {
            conditionMetrics.get(i).setText(data.conditions.get(i));
        }
        //container.validate();
        container.repaint();
        //Main.window.validate();
        Main.window.repaint();
    }
}
