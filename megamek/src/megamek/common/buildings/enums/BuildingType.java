/*
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
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
package megamek.common.buildings.enums;

import megamek.MegaMek;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum BuildingType {
    //region Enum Declarations
    LIGHT("BuildingType.LIGHT.text", "BuildingType.LIGHT.toolTipText"),
    MEDIUM("BuildingType.MEDIUM.text", "BuildingType.MEDIUM.toolTipText"),
    HEAVY("BuildingType.HEAVY.text", "BuildingType.HEAVY.toolTipText"),
    HARDENED("BuildingType.HARDENED.text", "BuildingType.HARDENED.toolTipText"),
    WALL("BuildingType.WALL.text", "BuildingType.WALL.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    BuildingType(final String name, final String toolTipText) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isLight() {
        return this == LIGHT;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isHeavy() {
        return this == HEAVY;
    }

    public boolean isHardened() {
        return this == HARDENED;
    }

    public boolean isWall() {
        return this == WALL;
    }
    //endregion Boolean Comparison Methods

    //region File I/O
    public static BuildingType parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 1:
                    return LIGHT;
                case 2:
                    return MEDIUM;
                case 3:
                    return HEAVY;
                case 4:
                    return HARDENED;
                case 5:
                    return WALL;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a BuildingType. Returning LIGHT.");

        return LIGHT;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
