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

import megamek.client.ui.swing.util.ImageFileFactory;
import megamek.client.ui.swing.util.PlayerColors;
import megamek.common.Configuration;
import megamek.common.IPlayer;
import megamek.common.annotations.Nullable;
import megamek.common.logging.DefaultMmLogger;
import megamek.common.logging.MMLogger;
import megamek.common.util.DirectoryItems;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Camouflage implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = -5869844345380877898L;

    private static final MMLogger logger = DefaultMmLogger.getInstance();

    //region Constants
    public static final String NO_CAMO = "-- No Camo --"; // The no camouflage category
    public static final String ROOT_CAMO = "-- General --"; // The save category for camouflage in the root directory
    public static final String ROOT_CAMO_CATEGORY = ""; // The processing category for camouflage in the root directory
    public static final String COLOURED_CAMO = "Coloured Camo";
    private static final String SEPARATOR = ",";
    //endregion Constants

    private static DirectoryItems camouflageDirectory;

    private String category;
    private String fileName;
    //endregion Variable Declarations

    //region Constructors
    public Camouflage() {
        this(ROOT_CAMO, NO_CAMO);
    }

    public Camouflage(String category, String fileName) {
        // Must be a null, and not an empty string, if it isn't being used - Dylan 2014-04-04
        this.category = ("".equals(category)) ? null : category;
        this.fileName = ("".equals(fileName)) ? null : fileName;
    }
    //endregion Constructors

    //region Getters/Setters
    public static DirectoryItems getCamouflageDirectory() {
        return camouflageDirectory;
    }

    public static void createCamouflageDirectory() {
        try {
            camouflageDirectory = new DirectoryItems(Configuration.camoDir(), "",
                    ImageFileFactory.getInstance());
        } catch (Exception e) {
            camouflageDirectory = null;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(@Nullable String category) {
        this.category = category;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //endregion Getters/Setters

    public ImageIcon getImageIcon() {
        return new ImageIcon(getImage());
    }

    public @Nullable Image getImage() {
        String fileName = getFileName();
        if (getCategory() == null) {
            return null;
        } else if (!(COLOURED_CAMO.equals(getCategory()))) {
            String category = getCategory();
            // Translate the root camouflage directory name
            if (ROOT_CAMO.equals(category)) {
                category = ROOT_CAMO_CATEGORY;
            }

            // Try to get the camouflage file
            try {
                return (Image) camouflageDirectory.getItem(category, fileName);
            } catch (Exception e) {
                logger.error(getClass(), "getImage", "Failed to get the camouflage file", e);
            }

            // return the default colour
            fileName = IPlayer.colorNames[0];
        }

        BufferedImage tempImage = new BufferedImage(84, 72,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = tempImage.createGraphics();
        graphics.setColor(PlayerColors.getColor(fileName));
        graphics.fillRect(0, 0, 84, 72);
        return tempImage;
    }

    //region File IO
    public boolean isNull() {
        return (getCategory() == null) && (getFileName() == null);
    }

    public static Camouflage parseFromString(String text) {
        String[] split = text.split(SEPARATOR);

        if (split.length == 2) {
            return new Camouflage(split[0], split[1]);
        } else if ((split.length == 0) && text.equals(SEPARATOR)) {
            return new Camouflage(null, null);
        } else {
            logger.error(Camouflage.class, "parseFromString",
                    "Cannot parse Camouflage from a string of length " + split.length);
            return null;
        }
    }
    //endregion File IO

    @Override
    public String toString() {
        String category = getCategory();
        if (category == null) {
            category = "";
        }
        String fileName = getFileName();
        if (fileName == null) {
            fileName = "";
        }
        return category + SEPARATOR + fileName;
    }
}
