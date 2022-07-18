/*
 * Copyright (c) 2021-2022 - The MegaMek Team. All Rights Reserved.
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

import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

public enum Fog {
    //region Enum Declarations
    NONE("Fog.NONE.text", "Fog.NONE.toolTipText"),
    LIGHT("Fog.LIGHT.text", "Fog.LIGHT.toolTipText"),
    HEAVY("Fog.HEAVY.text", "Fog.HEAVY.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    Fog(final String name, final String toolTipText) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
                PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }
    //endregion Getters

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
    public static Fog parseFromString(final String text) {
        try {
            return valueOf(text);
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

        LogManager.getLogger().error("Unable to parse " + text + " into a Fog. Returning NONE.");

        return NONE;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
