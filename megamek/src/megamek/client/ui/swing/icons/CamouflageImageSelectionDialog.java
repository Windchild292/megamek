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

import megamek.client.ui.Messages;
import megamek.common.IPlayer;
import megamek.common.icons.AbstractIcon;

import javax.swing.*;
import java.util.Iterator;

public class CamouflageImageSelectionDialog extends AbstractImageSelectionDialog {
    public CamouflageImageSelectionDialog(JFrame parent, JButton button) {
        super(parent, button, Messages.getString("CamouflageChoiceDialog.SELECT_CAMOUFLAGE.text"));
    }

    @Override
    protected void fillTable(String category) {
        imageTableModel.reset();
        imageTableModel.setCategory(category);

        if (Camouflage.COLOURED_CAMO.equals(category) || Camouflage.NO_CAMO.equals(category)) {
            for (String color : IPlayer.colorNames) {
                imageTableModel.addIcon(new Camouflage(category, color));
            }
        } else {
            // Translate the "root camo" category name.
            Iterator<String> camoNames;
            if (Camouflage.ROOT_CAMO.equals(category)) {
                camoNames = Camouflage.getCamouflageDirectory().getItemNames(AbstractIcon.ROOT_CATEGORY);
            } else {
                camoNames = Camouflage.getCamouflageDirectory().getItemNames(category);
            }

            // Get the camo names for this category.
            while (camoNames.hasNext()) {
                imageTableModel.addIcon(new Camouflage(category, camoNames.next()));
            }
        }

        if (imageTableModel.getRowCount() > 0) {
            imageTable.setRowSelectionInterval(0, 0);
        }
        scrollImage.repaint();
    }
}
