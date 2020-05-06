/*
 * Copyright (c) 2020 - The MegaMek Team. All Rights Reserved.
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.client.ui.swing.camouflage;

import megamek.client.ui.Messages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CamouflageTableModel extends AbstractTableModel {
    //region Variable Declarations
    private static final long serialVersionUID = 7298823592090412589L;

    private String[] columnNames;
    private String category;
    private List<Camouflage> data;
    //endregion Variable Declarations

    public CamouflageTableModel() {
        columnNames = new String[] { Messages.getString("CamouflageTableModel.Camouflages") };
        category = Camouflage.NO_CAMO;
        data = new ArrayList<>();
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public void reset() {
        category = Camouflage.NO_CAMO;
        data = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row);
    }

    public void setCategory(String c) {
        category = c;
    }

    public String getCategory() {
        return category;
    }

    public void addCamouflage(Camouflage name) {
        data.add(name);
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public CamouflageTableModel.Renderer getRenderer() {
        return new CamouflageTableModel.Renderer();
    }

    public class Renderer extends JCamouflagePanel implements TableCellRenderer {
        private static final long serialVersionUID = 7483367362943393067L;

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Camouflage camouflage = (Camouflage) getValueAt(row, column);
            setText(camouflage);
            setImage(camouflage);

            if (isSelected) {
                setBackground(UIManager.getColor("Table.selectionBackground"));
                setForeground(UIManager.getColor("Table.selectionForeground"));
            } else {
                setBackground(UIManager.getColor("Table.background"));
                setForeground(UIManager.getColor("Table.foreground"));
            }

            return this;
        }
    }
}
