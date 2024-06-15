package org.example;

import javax.swing.*;
import java.awt.*;


/**
 * A custom JLabel class that extends from JLabel and overrides
 * most of the default properties and defining a more modern UI
 */
public class CustomLabel extends JLabel {

    CustomLabel(String label) {
        super(label);
        setForeground(Color.WHITE);
        setBorder(null);
        setBackground(new Color(20, 30, 40));
    }

    CustomLabel() {
        this("");
    }

    CustomLabel(String label, int alignment) {
        super(label, alignment);
    }
}
