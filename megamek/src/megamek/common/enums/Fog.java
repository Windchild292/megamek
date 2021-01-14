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
package megamek.common.enums;

import megamek.MegaMek;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Fog {
    //region Enum Declarations
    NONE("Fog.NONE.text"),
    LIGHT("Fog.LIGHT.text"),
    HEAVY("Fog.HEAVY.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.enums", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Fog(String name) {
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparisons
    public boolean isNone() {
        return this == NONE;
    }

    public boolean isLight() {
        return this == LIGHT;
    }

    public boolean isHeavy() {
        return this == HEAVY;
    }
    //endregion Boolean Comparisons

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Fog, or NONE if there is an error in parsing
     */
    public static Fog parseFromString(String text) {
        try {
            valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 1:
                    return LIGHT;
                case 2:
                    return HEAVY;
                case 0:
                default:
                    return NONE;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a Fog. Returning NONE.");

        return NONE;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
