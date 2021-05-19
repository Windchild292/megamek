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

public enum AtmosphericPressure {
    //region Enum Declarations
    VACUUM("AtmosphericPressure.VACUUM.text"),
    TRACE("AtmosphericPressure.TRACE.text"),
    THIN("AtmosphericPressure.THIN.text"),
    STANDARD("AtmosphericPressure.STANDARD.text"),
    HIGH("AtmosphericPressure.HIGH.text"),
    VERY_HIGH("AtmosphericPressure.VERY_HIGH.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    AtmosphericPressure(String name) {
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparisons
    public boolean isVacuum() {
        return this == VACUUM;
    }

    public boolean isTrace() {
        return this == TRACE;
    }

    public boolean isThin() {
        return this == THIN;
    }

    public boolean isStandard() {
        return this == STANDARD;
    }

    public boolean isHigh() {
        return this == HIGH;
    }

    public boolean isVeryHigh() {
        return this == VERY_HIGH;
    }

    public boolean isTraceOrVacuum() {
        return isTrace() || isVacuum();
    }
    //endregion Boolean Comparisons

    //region File I/O
    /**
     * @param text the string to parse
     * @return the AtmosphericPressure, or STANDARD if there is an error in parsing
     */
    public static AtmosphericPressure parseFromString(String text) {
        try {
            valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return VACUUM;
                case 1:
                    return TRACE;
                case 2:
                    return THIN;
                case 4:
                    return HIGH;
                case 5:
                    return VERY_HIGH;
                case 3:
                default:
                    return STANDARD;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into an AtmosphericPressure. Returning STANDARD.");

        return STANDARD;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
