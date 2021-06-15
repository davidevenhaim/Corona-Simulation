/**
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package UI;

import Virus.VirusMutations;

import javax.swing.table.AbstractTableModel;

class BooleanTableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data; // get reference of the object and not a deep copy!

    public BooleanTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        data[rowIndex][columnIndex] = aValue;
    }

    // This method is used by the JTable to define the default
    // renderer or editor for each cell. For example if you have
    // a boolean data it will be rendered as a check box. A
    // number value is right aligned.
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }
}