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
package megamek.client.ui.swing.icons;

import megamek.common.icons.AbstractIcon;
import megamek.common.util.fileUtils.DirectoryItems;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractImageTableModel extends AbstractTableModel {
    //region Variable Declarations
    private static final long serialVersionUID = 7298823592090412589L;

    private String[] columnNames;
    private String category;
    private List<AbstractIcon> data;
    private transient final DirectoryItems imageDirectory;
    //endregion Variable Declarations

    protected AbstractImageTableModel(DirectoryItems imageDirectory, String... columnNames) {
        this.columnNames = columnNames;
        this.imageDirectory = imageDirectory;
        category = AbstractIcon.ROOT_CATEGORY;
        data = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public void reset() {
        category = AbstractIcon.ROOT_CATEGORY;
        data = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row);
    }

    public void setCategory(String c) {
        category = c;
    }

    public String getCategory() {
        return category;
    }


    public DirectoryItems getImageDirectory() {
        return imageDirectory;
    }

    public void addIcon(AbstractIcon icon) {
        data.add(icon);
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

    public AbstractImageTableModel.Renderer getRenderer() {
        return new AbstractImageTableModel.Renderer(getImageDirectory());
    }

    public class Renderer extends AbstractImagePanel implements TableCellRenderer {
        private static final long serialVersionUID = 7483367362943393067L;

        protected Renderer(DirectoryItems imageDirectory) {
            super(imageDirectory);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            AbstractIcon icon = (AbstractIcon) getValueAt(row, column);
            setIcon(icon);
            updatePanel();

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
