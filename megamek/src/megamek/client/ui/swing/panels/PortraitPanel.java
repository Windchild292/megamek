/*
 * Copyright (c) 2020 - The MegaMek Team. All Rights Reserved.
 *
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
package megamek.client.ui.swing.panels;

import megamek.MegaMek;
import megamek.common.Crew;
import megamek.common.util.fileUtils.DirectoryItems;

import javax.swing.*;
import java.awt.*;

public class PortraitPanel extends JPanel {
    private String portraitCategory;
    private String portraitFileName;
    private int scale;
    private DirectoryItems portraitDirectory;
    private JLabel lblPortrait;

    public PortraitPanel(DirectoryItems portraitDirectory) {
        this(portraitDirectory, Crew.ROOT_PORTRAIT, Crew.PORTRAIT_NONE, 0);
    }

    public PortraitPanel(DirectoryItems portraitDirectory, String portraitCategory,
                         String portraitFileName, int scale) {
        setPortraitDirectory(portraitDirectory);
        setPortraitCategory(portraitCategory);
        setPortraitFileName(portraitFileName);
        setScale(scale);

        initComponents();
        updatePanel();
    }

    //region Initialization
    private void initComponents() {
        // Panel portrait will include the person picture and the ribbons
        setName("PortraitPanel");
        setLayout(new GridBagLayout());

        lblPortrait = new JLabel();
        lblPortrait.setName("lblPortrait");

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(lblPortrait, gridBagConstraints);
    }
    //endregion Initialization

    //region Getters/Setters
    private DirectoryItems getPortraitDirectory() {
        return portraitDirectory;
    }

    public void setPortraitDirectory(DirectoryItems portraitDirectory) {
        this.portraitDirectory = portraitDirectory;
    }

    public String getPortraitCategory() {
        return portraitCategory;
    }

    public void setPortraitCategory(String portraitCategory) {
        this.portraitCategory = portraitCategory;
    }

    public String getPortraitFileName() {
        return portraitFileName;
    }

    public void setPortraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    private Image getImage() {
        String category = (Crew.ROOT_PORTRAIT.equals(getPortraitCategory())) ? "" : getPortraitCategory();
        String filename = getPortraitFileName();

        // Return the default image if the player has selected no portrait file.
        if ((category == null) || (filename == null) || Crew.PORTRAIT_NONE.equals(filename)) {
            filename = "default.gif";
        }

        // Try to get the player's portrait file.
        Image portrait = null;
        try {
            portrait = (Image) getPortraitDirectory().getItem(category, filename);
            if (portrait == null) {
                portrait = (Image) getPortraitDirectory().getItem("", "default.gif");
            }
        } catch (Exception e) {
            MegaMek.getLogger().error(getClass(), "setPortrait", e);
        }

        return portrait;
    }

    private Image getScaledImage() {
        Image portrait = getImage();
        if (portrait != null) {
            return portrait.getScaledInstance(getScale(), -1, Image.SCALE_DEFAULT);
        } else {
            return null;
        }
    }
    //endregion Getters/Setters

    public void updatePanel() {
        lblPortrait.setIcon(new ImageIcon((getScale() == 0) ? getImage() : getScaledImage()));
    }

    public void setText(String text) {
        lblPortrait.setText(text);
    }
}
