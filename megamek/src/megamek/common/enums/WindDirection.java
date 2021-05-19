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

public enum WindDirection {
    //region Enum Declarations
    NORTH("WindDirection.NORTH.text"),
    NORTHEAST("WindDirection.NORTHEAST.text"),
    SOUTHEAST("WindDirection.SOUTHEAST.text"),
    SOUTH("WindDirection.SOUTH.text"),
    SOUTHWEST("WindDirection.SOUTHWEST.text"),
    NORTHWEST("WindDirection.NORTHWEST.text"),
    RANDOMIZE("WindDirection.RANDOMIZE.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    WindDirection(String name) {
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparisons
    public boolean isNorth() {
        return this == NORTH;
    }

    public boolean isNortheast() {
        return this == NORTHEAST;
    }

    public boolean isSoutheast() {
        return this == SOUTHEAST;
    }

    public boolean isSouth() {
        return this == SOUTH;
    }

    public boolean isSouthwest() {
        return this == SOUTHWEST;
    }

    public boolean isNorthwest() {
        return this == NORTHWEST;
    }

    public boolean isRandomize() {
        return this == RANDOMIZE;
    }
    //endregion Boolean Comparisons

    //region File I/O
    /**
     * @param text the string to parse
     * @return the WindDirection, or RANDOMIZE if there is an error in parsing
     */
    public static WindDirection parseFromString(String text) {
        try {
            valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return NORTH;
                case 1:
                    return NORTHEAST;
                case 2:
                    return SOUTHEAST;
                case 3:
                    return SOUTH;
                case 4:
                    return SOUTHWEST;
                case 5:
                    return NORTHWEST;
                case 6:
                default:
                    return RANDOMIZE;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into an WindDirection. Returning RANDOMIZE.");

        return RANDOMIZE;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
