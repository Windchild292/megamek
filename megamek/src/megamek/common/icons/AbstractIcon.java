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
package megamek.common.icons;

import megamek.MegaMek;
import megamek.utils.MegaMekXmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.Serializable;

public abstract class AbstractIcon implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 870271199001476289L;

    public static final String ROOT_CATEGORY = "-- General --";
    public static final String DEFAULT_ICON_FILENAME = "None";
    public static final int DEFAULT_IMAGE_SCALE = 75;

    private String category;
    private String fileName;

    private int width;
    private int height;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractIcon() {
        this(ROOT_CATEGORY, DEFAULT_ICON_FILENAME);
    }

    protected AbstractIcon(String category, String fileName) {
        this(category, fileName, 0, 0);
    }

    protected AbstractIcon(String category, String fileName, int width, int height) {
        setCategory(category);
        setFileName(fileName);
        setWidth(width);
        setHeight(height);
    }
    //endregion Constructors

    //region Getters/Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    //endregion Getters/Setters

    /**
     * This is used to determine whether the created image should be scaled or not by checking the
     * Height and Width values. If either is a 0, then we need to scale the produced image
     * @return whether to scale the image or not
     */
    protected boolean isScaled() {
        return (getHeight() == 0) || (getWidth() == 0);
    }

    /**
     * @return the ImageIcon for the Image stored by the AbstractIcon
     */
    public ImageIcon getImageIcon() {
        return new ImageIcon(getImage());
    }

    /**
     * This is used to create the proper image and scale it if required. It also handles null protection
     * by creating a blank image if required.
     * @return the created image
     */
    public Image getImage() {
        Image image = getBaseImage();

        if (image == null) {
            return createBlankImage();
        } else if (isScaled()) {
            return image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        } else {
            return image;
        }
    }

    /**
     * This is abstract to allow for different formats for determining the image in question
     * @return the Image stored by the AbstractIcon
     */
    public abstract Image getBaseImage();

    /**
     * This is a utility method that creates a blank image in the case that no image is found.
     * @return a clear blank image
     */
    protected Image createBlankImage() {
        final int width = (getWidth() == 0) ? DEFAULT_IMAGE_SCALE : getWidth();
        final int height = (getHeight() == 0) ? DEFAULT_IMAGE_SCALE : getHeight();
        BufferedImage blankImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = blankImage.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);

        return blankImage;
    }

    @Override
    public String toString() {
        return getCategory() + "/" + getFileName();
    }

    //region File IO
    /**
     * This writes the AbstractIcon to XML
     * @param pw1 the PrintWriter to write to
     * @param indent the indentation of the first line
     * @param nodeTitle the title to use for the node title
     */
    public void writeToXML(PrintWriter pw1, int indent, String nodeTitle) {
        MegaMekXmlUtil.writeSimpleXMLOpenIndentedLine(pw1, indent, nodeTitle);
        MegaMekXmlUtil.writeSimpleXmlTag(pw1, indent + 1, "category", getCategory());
        MegaMekXmlUtil.writeSimpleXmlTag(pw1, indent + 1, "fileName", getFileName());
        MegaMekXmlUtil.writeSimpleXMLCloseIndentedLine(pw1, indent, nodeTitle);
    }

    /**
     * This is used to parse an AbstractIcon from a saved XML node
     * @param retVal the AbstractIcon to parse into
     * @param wn the node to parse from
     * @return the parsed AbstractIcon
     */
    public static AbstractIcon parseFromXML(AbstractIcon retVal, Node wn) {
        try {
            NodeList nl = wn.getChildNodes();

            for (int x = 0; x < nl.getLength(); x++) {
                Node wn2 = nl.item(x);

                if (wn2.getNodeName().equalsIgnoreCase("category")) {
                    retVal.setCategory(wn2.getTextContent().trim());
                } else if (wn2.getNodeName().equalsIgnoreCase("fileName")) {
                    retVal.setFileName(wn2.getTextContent().trim());
                }
            }
        } catch (Exception e) {
            MegaMek.getLogger().error(AbstractIcon.class, "Failed to parse icon from nodes");
            MegaMek.getLogger().error(AbstractIcon.class, e);
        }

        return retVal;
    }
    //endregion File IO

    public boolean isDefault() {
        return getCategory().equals(ROOT_CATEGORY) && getFileName().equals(DEFAULT_ICON_FILENAME);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof AbstractIcon) {
            AbstractIcon dOther = (AbstractIcon) other;

            return dOther.getCategory().equals(getCategory()) && dOther.getFileName().equals(getFileName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (getCategory() + getFileName()).hashCode();
    }
}
