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

import javax.swing.*;
import java.awt.*;

public class JCamouflagePanel extends JPanel {
    //region Variable Declarations
    private static final long serialVersionUID = 6850715473654649719L;

    private JLabel lblImage;
    //endregion Variable Declarations

    public JCamouflagePanel() {
        setLayout(new GridBagLayout());

        lblImage = new JLabel("");
        lblImage.setName("lblImage");

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(lblImage, gridBagConstraints);
    }

    public void setFromCamouflage(Camouflage camouflage) {
        lblImage.setText(camouflage.getFileName());
        lblImage.setIcon(camouflage.getImageIcon());
    }
}
