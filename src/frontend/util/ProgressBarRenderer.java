package frontend.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

// Custom renderer for progress bar
public class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressBarRenderer() {
        super(0, 100);
        setStringPainted(true); // Show percentage text
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        int progress = 0;
        if (value instanceof Integer) {
            progress = (Integer) value;
        }

        setValue(progress);
        setString(progress + "%");

        // Change color based on progress
        if (progress < 30) {
            setForeground(Color.RED);
        } else if (progress < 70) {
            setForeground(Color.ORANGE);
        } else {
            setForeground(Color.GREEN);
        }

        // Background color for selection
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }

        return this;
    }
}