package org.example;

import javax.swing.*;
import java.awt.*;


/**
 * This class defines the Navigation section
 * of the GUI. It defines to navigations, the history and weather.
 */
public class Navigation extends RoundedPanel {
    public Navigation() {
        super(100, 550, new Color(20, 30, 40));
        setLayout(new GridBagLayout());
        JButton weather = new JButton("Weather");
        JButton history = new JButton("History");
        weather.setForeground(Color.WHITE);
        weather.setBorder(null);
        weather.setFocusPainted(false);
        weather.setSelected(true);
        weather.setBackground(new Color(20, 30, 40));
        weather.setPreferredSize(new Dimension(90, 20));
        history.setPreferredSize(new Dimension(90, 20));
        history.setBackground(new Color(20, 30, 40));
        history.setForeground(Color.WHITE);
        history.setFocusPainted(false);
        history.setBorder(null);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        //c.ipadx = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(10, 2, 2, 2);
        add(weather, c);
        c.gridy = 1;
        c.weighty = 1;
        add(history, c);
    }
}
