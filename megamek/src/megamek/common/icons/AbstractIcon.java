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

import megamek.common.logging.DefaultMmLogger;
import megamek.common.logging.MMLogger;
import megamek.common.util.DirectoryItems;
import megamek.utils.MegaMekXmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.Serializable;

public abstract class AbstractIcon implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 870271199001476289L;

    public static final String ROOT_CATEGORY = "-- General --";
    public static final String DEFAULT_ICON = "None";

    private static final MMLogger logger = DefaultMmLogger.getInstance();

    private transient DirectoryItems directory;
    private String category;
    private String fileName;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractIcon(DirectoryItems directory) {
        this(directory, ROOT_CATEGORY, DEFAULT_ICON);
    }

    protected AbstractIcon(DirectoryItems directory, String category, String fileName) {
        this.directory = directory;
        this.category = category;
        this.fileName = fileName;
    }
    //endregion Constructors

    //region Getters/Setters
    /**
     * @return the DirectoryItems stored with the AbstractIcon
     */
    public DirectoryItems getDirectory() {
        return directory;
    }

    /**
     * @param directory the DirectoryItems that will be used by this AbstractIcon
     */
    public void setDirectory(DirectoryItems directory) {
        this.directory = directory;
    }

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
    //endregion Getters/Setters

    /**
     * @return the ImageIcon for the Image stored by the AbstractIcon
     */
    public ImageIcon getImageIcon() {
        return new ImageIcon(getImage());
    }

    /**
     * This is abstract to allow for different formats for determining the image in question
     * @return the Image stored by the AbstractIcon
     */
    public abstract Image getImage();

    //region File IO
    /**
     * This writes the AbstractIcon to XML
     * @param pw1 the PrintWriter to write to
     * @param indent the indentation of the first line
     */
    public void writeToXML(PrintWriter pw1, int indent) {
        MegaMekXmlUtil.writeSimpleXMLOpenIndentedLine(pw1, indent, "AbstractIcon");
        MegaMekXmlUtil.writeSimpleXmlTag(pw1, indent + 1, "category", getCategory());
        MegaMekXmlUtil.writeSimpleXmlTag(pw1, indent + 1, "fileName", getFileName());
        MegaMekXmlUtil.writeSimpleXMLCloseIndentedLine(pw1, indent, "AbstractIcon");
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
            logger.error(AbstractIcon.class, "parseFromXML", "Failed to parse icon from nodes", e);
        }

        return retVal;
    }
    //endregion File IO

    @Override
    public String toString() {
        return getCategory() + "/" + getFileName();
    }
}

