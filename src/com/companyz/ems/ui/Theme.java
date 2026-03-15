package com.companyz.ems.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Theme {
    // Color Palette - Modern Dark Blue / Teal
    public static final Color PRIMARY = new Color(25, 42, 86);
    public static final Color PRIMARY_LIGHT = new Color(36, 59, 112);
    public static final Color ACCENT = new Color(0, 168, 150);
    public static final Color ACCENT_HOVER = new Color(0, 195, 175);
    public static final Color SIDEBAR_BG = new Color(18, 30, 62);
    public static final Color SIDEBAR_HOVER = new Color(36, 55, 100);
    public static final Color SIDEBAR_ACTIVE = new Color(0, 168, 150);
    public static final Color CONTENT_BG = new Color(240, 243, 247);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    public static final Color TEXT_LIGHT = new Color(206, 212, 218);
    public static final Color TEXT_WHITE = Color.WHITE;
    public static final Color DANGER = new Color(220, 53, 69);
    public static final Color DANGER_HOVER = new Color(200, 35, 51);
    public static final Color SUCCESS = new Color(40, 167, 69);
    public static final Color WARNING = new Color(255, 193, 7);
    public static final Color TABLE_HEADER_BG = new Color(52, 73, 126);
    public static final Color TABLE_ALT_ROW = new Color(245, 248, 252);
    public static final Color TABLE_HOVER = new Color(220, 235, 252);
    public static final Color BORDER_COLOR = new Color(222, 226, 230);
    public static final Color INPUT_BG = new Color(248, 249, 250);
    public static final Color INPUT_BORDER = new Color(206, 212, 218);
    public static final Color INPUT_FOCUS = new Color(0, 168, 150);

    // Fonts
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_SIDEBAR = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_SIDEBAR_HEADER = new Font("SansSerif", Font.BOLD, 11);
    public static final Font FONT_TABLE = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_TABLE_HEADER = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 13);

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(ACCENT);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 30, 38));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT); }
        });
        return btn;
    }

    public static JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(DANGER);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 30, 38));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(DANGER_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(DANGER); }
        });
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(CARD_BG);
        btn.setForeground(PRIMARY);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 30, 38));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY);
                btn.setForeground(TEXT_WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(CARD_BG);
                btn.setForeground(PRIMARY);
            }
        });
        return btn;
    }

    public static JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(FONT_BODY);
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INPUT_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_FOCUS, 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_BORDER, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        });
        return field;
    }

    public static JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(FONT_BODY);
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INPUT_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_FOCUS, 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_BORDER, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        });
        return field;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_BODY_BOLD);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JLabel createSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_SUBTITLE);
        label.setForeground(PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setRowHeight(36);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(TEXT_WHITE);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setBackground(CARD_BG);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? CARD_BG : TABLE_ALT_ROW);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(TEXT_WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                l.setBackground(TABLE_HEADER_BG);
                l.setForeground(TEXT_WHITE);
                l.setFont(FONT_TABLE_HEADER);
                l.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(70, 90, 140)),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)
                ));
                l.setHorizontalAlignment(SwingConstants.LEFT);
                return l;
            }
        });
    }

    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));
        return card;
    }

    public static JPanel createContentWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        return wrapper;
    }

    public static JScrollPane createStyledScrollPane(JComponent comp) {
        JScrollPane sp = new JScrollPane(comp);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        sp.getViewport().setBackground(CARD_BG);
        return sp;
    }
}
