package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Defines the Five days forecast of the GUI
 */
public class WeatherFiveDays extends RoundedPanel {
    private static JPanel container = null;
    private JLabel wPic;
    RoundedPanel fiveForecast;
    private static ArrayList<JLabel> days = new ArrayList<>();
    private static final ArrayList<JLabel> imgLabels = new ArrayList<>();
    private static final ArrayList<JLabel> weatherLabels = new ArrayList<>();
    private static final ArrayList<CustomLabel> tempLabels = new ArrayList<>();

    public WeatherFiveDays() {
        super(350, 550, new Color(20, 30, 40));
        container = this;
        //setBorder(new RoundedBorder(50));
        setLayout(new GridBagLayout());
        CustomLabel fiveDaysLabel = new CustomLabel("5-DAY FORECAST");
        fiveDaysLabel.setPreferredSize(new Dimension(296, 40));


        fiveForecast = new RoundedPanel(296, 300, new Color(20, 30, 40));
        fiveForecast.setPreferredSize(new Dimension(296, 300));
        fiveForecast.setLayout(new GridLayout(5, 1));
        addFiveForecast();


        RoundedPanel wPicPanel = new RoundedPanel(246, 200, new Color(20, 30, 40));
        wPicPanel.setPreferredSize(new Dimension(246, 200));
        wPicPanel.setLayout(new GridBagLayout());
        wPic = new JLabel();
        wPic.setIcon(new ImageIcon(Objects.requireNonNull(WeatherDetails.getWeatherImage(true))));
        wPic.setPreferredSize(new Dimension(246, 100));
        wPicPanel.add(wPic);

        GridBagConstraints cFiveDays = new GridBagConstraints();
        cFiveDays.gridx = 0;
        cFiveDays.gridy = 0;
        add(fiveDaysLabel, cFiveDays);

        cFiveDays.gridy = 1;
        add(fiveForecast, cFiveDays);

        cFiveDays.gridy = 2;
        add(wPicPanel, cFiveDays);
    }

    private void addFiveForecast() {
        for (int i = 0; i < 5; i++) {
            RoundedPanel oneForecast = new RoundedPanel(296, 49, new Color(20, 30, 40));
            if (i < 4) oneForecast.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(150, 150, 150)));
            oneForecast.setLayout(new GridLayout(1, 3));
            CustomLabel day = new CustomLabel("Today");
            days.add(day);

            RoundedPanel imgPanel = new RoundedPanel(98, 49, new Color(20, 30, 40));
            imgPanel.setLayout(new BorderLayout());
            JLabel wImg = new JLabel();
            wImg.setIcon(new ImageIcon(Objects.requireNonNull(WeatherDetails.getWeatherImage(false))));
            imgLabels.add(wImg);
            CustomLabel wLabel = new CustomLabel("Sunny");
            weatherLabels.add(wLabel);
            imgPanel.add(wImg, BorderLayout.WEST);
            imgPanel.add(wLabel, BorderLayout.EAST);


            RoundedPanel tempPanel = new RoundedPanel(98, 49, new Color(20, 30, 40));
            tempPanel.setLayout(new BorderLayout());
            CustomLabel temp = new CustomLabel("26/21");
            tempLabels.add(temp);
            tempPanel.add(temp, BorderLayout.EAST);

            oneForecast.add(day);
            oneForecast.add(imgPanel);
            oneForecast.add(tempPanel);
            fiveForecast.add(oneForecast);
        }
    }

    public static void updateFiveData(ArrayList<String> days, ArrayList<String> codes, ArrayList<String> des, ArrayList<String> humids) {
        System.out.println("size of days is: " + days.size());
        for (int i = 0; i < days.size(); i++) {
            WeatherFiveDays.days.get(i).setText(days.get(i));
            imgLabels.get(i).setIcon(new ImageIcon(Objects.requireNonNull(WeatherDetails.getWeatherImage(codes.get(i), false))));
            weatherLabels.get(i).setText(des.get(i));
            tempLabels.get(i).setText(humids.get(i));
            container.validate();
            container.repaint();
        }
    }
}
