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

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public abstract class AbstractImageSelectionDialog extends JDialog implements TreeSelectionListener {
    //region Variable Declarations
    private static final long serialVersionUID = 9220162367683378065L;

    protected JSplitPane splitPane;

    protected JScrollPane scrollImage;
    protected JButton sourceButton;
    protected JTree treeCategories;
    protected AbstractImageTableModel imageTableModel;
    protected JTable imageTable;
    //endregion Variable Declarations

    public AbstractImageSelectionDialog(JFrame parent, JButton button, String dialogName) {
        // Initialize our superclass and record our parent frame
        super(parent, dialogName, true);
    }

    protected abstract void fillTable(String category);

    @Override
    public void valueChanged(TreeSelectionEvent ev) {
        if (ev.getSource().equals(treeCategories)) {
            TreePath[] paths = treeCategories.getSelectionPaths();
            // If nothing is selected, there's nothing to populate the table with.
            if (paths == null) {
                return;
            }
            for (TreePath path : paths) {
                Object[] values = path.getPath();
                StringBuilder category = new StringBuilder();
                for (int i = 1; i < values.length; i++) {
                    if (values[i] != null) {
                        String name = (String) ((DefaultMutableTreeNode) values[i]).getUserObject();
                        category.append(name);
                        if (!name.equals(AbstractIcon.ROOT_CATEGORY)) {
                            category.append("/");
                        }
                    }
                }
                fillTable(category.toString());
            }
        }
    }
}