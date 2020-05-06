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
import megamek.common.IPlayer;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.Iterator;

public class CamouflageChoiceDialog extends JDialog implements TreeSelectionListener {
    //region Variable Declarations
    private static final long serialVersionUID = 9220162367683378065L;

    public JSplitPane splitPane;

    private JScrollPane scrollCamouflage;
    private JButton sourceButton;
    private JTree treeCategories;
    private CamouflageTableModel camouflageTableModel;
    private JTable tableCamouflage;
    //endregion Variable Declarations

    public CamouflageChoiceDialog(JFrame parent, JButton button) {
        // Initialize our superclass and record our parent frame.
        super(parent, Messages.getString("CamouflageChoiceDialog.SELECT_CAMOUFLAGE.text"), true);

    }

    private void fillTable(String category) {
        camouflageTableModel.reset();
        camouflageTableModel.setCategory(category);
        if (Camouflage.COLOURED_CAMO.equals(category) || Camouflage.NO_CAMO.equals(category)) {
            for (String color : IPlayer.colorNames) {
                camouflageTableModel.addCamouflage(new Camouflage(category, color));
            }
        } else {
            // Translate the "root camo" category name.
            Iterator<String> camoNames;
            if (Camouflage.ROOT_CAMO.equals(category)) {
                camoNames = Camouflage.getCamouflageDirectory().getItemNames(Camouflage.ROOT_CAMO_CATEGORY);
            } else {
                camoNames = Camouflage.getCamouflageDirectory().getItemNames(category);
            }

            // Get the camo names for this category.
            while (camoNames.hasNext()) {
                camouflageTableModel.addCamouflage(new Camouflage(category, camoNames.next()));
            }
        }
        if (camouflageTableModel.getRowCount() > 0) {
            tableCamouflage.setRowSelectionInterval(0, 0);
        }
        scrollCamouflage.repaint();
    }

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
                        if (!name.equals(Camouflage.NO_CAMO) && !name.equals(Camouflage.ROOT_CAMO)
                                && !name.equals(Camouflage.COLOURED_CAMO)) {
                            category.append("/");
                        }
                    }
                }
                fillTable(category.toString());
            }
        }
    }
}
