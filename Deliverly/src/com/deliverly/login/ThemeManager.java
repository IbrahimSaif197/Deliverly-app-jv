package com.deliverly.login;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class ThemeManager {

    private static final Preferences prefs = Preferences.userRoot().node("com.deliverly.settings");
    private static boolean isDarkMode = prefs.getBoolean("darkMode", false);


    private static final Color LIGHT_BG = new Color(204, 255, 255);
    private static final Color LIGHT_FG = Color.BLACK;


    private static final Color DARK_BG = new Color(45, 45, 45);
    private static final Color DARK_FG = Color.WHITE;


    public static void setDarkMode(JFrame frame) {
        isDarkMode = true;
        prefs.putBoolean("darkMode", true);
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUIColors(frame.getContentPane(), DARK_BG, DARK_FG);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }

    public static void setLightMode(JFrame frame) {
        isDarkMode = false;
        prefs.putBoolean("darkMode", false);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUIColors(frame.getContentPane(), LIGHT_BG, LIGHT_FG);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }

    public static boolean isDarkModeEnabled() {
        return isDarkMode;
    }

    private static void updateUIColors(Container container, Color bgColor, Color fgColor) {
        if (container == null) return;

        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                

                updateUIColors(panel, bgColor, fgColor);

            } else if (component instanceof JLabel || component instanceof JCheckBox) {
                component.setForeground(fgColor);
            } else if (component instanceof JButton) {
                component.setForeground(fgColor);
                component.setBackground(bgColor.darker());
            } else if (component instanceof JTextField || component instanceof JPasswordField) {
                component.setForeground(fgColor);
                component.setBackground(bgColor.brighter());
            } else if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                scrollPane.getViewport().setBackground(bgColor);
                updateUIColors(scrollPane.getViewport(), bgColor, fgColor);
            } else if (component instanceof JComboBox) {
                component.setForeground(fgColor);
                component.setBackground(bgColor);
            } else if (component instanceof JList) {
                component.setForeground(fgColor);
                component.setBackground(bgColor);
            } else if (component instanceof JTable) {
                JTable table = (JTable) component;
                table.setForeground(fgColor);

                if (bgColor.equals(DARK_BG)) { 
                    table.setBackground(DARK_BG);
                    table.getTableHeader().setBackground(DARK_BG.darker());
                } else { 
                    table.setBackground(Color.WHITE);
                    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
                }

                table.getTableHeader().setForeground(fgColor);
            } else if (component instanceof JTabbedPane) {
                JTabbedPane tabbedPane = (JTabbedPane) component;
                tabbedPane.setForeground(fgColor);
                tabbedPane.setBackground(bgColor);

                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    tabbedPane.setForegroundAt(i, fgColor);
                    tabbedPane.setBackgroundAt(i, bgColor);

                    Component tabComponent = tabbedPane.getComponentAt(i);
                    if (tabComponent instanceof JPanel) {
                        updateUIColors((JPanel) tabComponent, bgColor, fgColor);
                    }
                }
            } else if (component instanceof JMenuBar) {
                component.setForeground(fgColor);
                component.setBackground(bgColor);
            } else if (component instanceof JMenu) {
                component.setForeground(fgColor);
                component.setBackground(bgColor);
            } else if (component instanceof JMenuItem) {
                component.setForeground(fgColor);
                component.setBackground(bgColor);
            }
        }
    }

}
