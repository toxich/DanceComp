package com.improteam.dancecomp.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jury
 */
public class ColoredTableRenderer extends DefaultTableCellRenderer {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ColoredTableRenderer.class);

    private Map<Integer, Boolean> specialCols = new HashMap<>();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int grayScale = row % 2 == 0 ? 0xff : 0xe0;
        Boolean special = specialCols.get(column);
        setBackground(
                new Color(
                        grayScale,
                        grayScale - (Boolean.TRUE.equals(special) ? 10 : 0),
                        grayScale - (Boolean.FALSE.equals(special) ? 10 : 0)
                ));
        setForeground(new Color(0x00, 0x00, 0x00));
        return this;
    }

    public Map<Integer, Boolean> getSpecialCols() {
        return specialCols;
    }
}
