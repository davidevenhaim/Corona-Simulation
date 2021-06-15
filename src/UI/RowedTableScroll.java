package UI;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class RowedTableScroll extends JScrollPane {
    private static class RowHeaderRenderer extends JLabel implements ListCellRenderer<String> {

        RowHeaderRenderer(JTable table) {
            setOpaque(true); setBorder(UIManager.getBorder("TableHeader.cellBorder")); setHorizontalAlignment(CENTER);
            JTableHeader header = table.getTableHeader();
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }


    public RowedTableScroll(final JTable table, final String[] rowHeaders) {
        super(table);
            final JList<String> rowHeader = new JList<String>(new AbstractListModel<String>() {
                public int getSize() {
                    return Math.min(rowHeaders.length, table.getRowCount());
                }
                public String getElementAt(int index) {
                    return rowHeaders[index];
                } ;
            });
//            rowHeader.setFixedCellWidth();
            rowHeader.setFixedCellHeight(table.getRowHeight());
            rowHeader.setCellRenderer(new RowHeaderRenderer(table));
            this.setRowHeaderView(rowHeader);
    }
}

