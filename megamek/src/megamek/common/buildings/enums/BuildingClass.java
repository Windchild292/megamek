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

public enum BuildingClass {
    //region Enum Declarations
    STANDARD("BuildingClass.STANDARD.text", "BuildingClass.STANDARD.toolTipText"),
    HANGAR("BuildingClass.HANGAR.text", "BuildingClass.HANGAR.toolTipText"),
    FORTRESS("BuildingClass.FORTRESS.text", "BuildingClass.FORTRESS.toolTipText"),
    //CASTLE_BRIAN("BuildingClass.CASTLE_BRIAN.text", "BuildingClass.CASTLE_BRIAN.toolTipText"),
    GUN_EMPLACEMENT("BuildingClass.GUN_EMPLACEMENT.text", "BuildingClass.GUN_EMPLACEMENT.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    BuildingClass(final String name, final String toolTipText) {
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
    public boolean isStandard() {
        return this == STANDARD;
    }

    public boolean isHangar() {
        return this == HANGAR;
    }

    public boolean isFortress() {
        return this == FORTRESS;
    }

//    public boolean isCastleBrian() {
//        return this == CASTLE_BRIAN;
//    }

    public boolean isGunEmplacement() {
        return this == GUN_EMPLACEMENT;
    }
    //endregion Boolean Comparison Methods

    //region File I/O
    public static BuildingClass parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return STANDARD;
                case 1:
                    return HANGAR;
                case 2:
                    return FORTRESS;
                case 3:
                    return GUN_EMPLACEMENT;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a EnumName. Returning STANDARD.");

        return STANDARD;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
