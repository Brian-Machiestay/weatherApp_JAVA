package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * A custom class that redesigns a JPanel. It extends
 * the JPanel class and draws a rounded JPanel
 */
public class RoundedPanel extends JPanel {

    private Dimension arcs;
    private final int width, height;
    private Float strokeSize;
    private Color bg;

    public RoundedPanel(int width, int height, Color bg) {
        arcs = new Dimension(30, 30);
        strokeSize = 1.0f;
        this.width = width;
        this.height = height;
        this.bg = bg;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        g2.setStroke(new BasicStroke(strokeSize));
        //g2.setColor(bg);
        g2.drawRoundRect(0, 0, width, height, arcs.width, arcs.height);
        g2.setStroke(new BasicStroke());
    }
}
