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

import megamek.common.Entity;
import megamek.common.EntityWeightClass;
import megamek.common.Mech;
import megamek.common.Protomech;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

public enum AtmosphericPressure {
    //region Enum Declarations
    VACUUM("AtmosphericPressure.VACUUM.text", "AtmosphericPressure.VACUUM.toolTipText"),
    TRACE("AtmosphericPressure.TRACE.text", "AtmosphericPressure.TRACE.toolTipText"),
    THIN("AtmosphericPressure.THIN.text", "AtmosphericPressure.THIN.toolTipText"),
    STANDARD("AtmosphericPressure.STANDARD.text", "AtmosphericPressure.STANDARD.toolTipText"),
    HIGH("AtmosphericPressure.HIGH.text", "AtmosphericPressure.HIGH.toolTipText"),
    VERY_HIGH("AtmosphericPressure.VERY_HIGH.text", "AtmosphericPressure.VERY_HIGH.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    AtmosphericPressure(final String name, final String toolTipText) {
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

    public int getPartialWingJumpBonus(final Entity entity) {
        if (entity instanceof Mech) {
            if (entity.getWeightClass() > EntityWeightClass.WEIGHT_MEDIUM) {
                switch (this) {
                    case VACUUM:
                    case TRACE:
                    case THIN:
                        return 0;
                    case HIGH:
                    case VERY_HIGH:
                        return 2;
                    case STANDARD:
                    default:
                        return 1;
                }
            } else {
                switch (this) {
                    case THIN:
                        return 1;
                    case STANDARD:
                    case HIGH:
                        return 2;
                    case VERY_HIGH:
                        return 3;
                    case VACUUM:
                    case TRACE:
                    default:
                        return 0;
                }
            }
        } else if (entity instanceof Protomech) {
            switch (this) {
                case TRACE:
                    return 1;
                case THIN:
                case STANDARD:
                    return 2;
                case HIGH:
                case VERY_HIGH:
                    return 3;
                default:
                    return 0;
            }
        } else {
            LogManager.getLogger().error("Illegal entity type of " + entity.getClass()
                    + " for partial wing jump modifier, returning 0");
            return 0;
        }
    }

    public int getPartialWingHeatBonus() {
        switch (this) {
            case VACUUM:
                return 0;
            case TRACE:
                return 1;
            case THIN:
                return 2;
            case STANDARD:
            case HIGH:
            case VERY_HIGH:
            default:
                return 3;
        }
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the AtmosphericPressure, or STANDARD if there is an error in parsing
     */
    public static AtmosphericPressure parseFromString(final String text) {
        try {
            return valueOf(text);
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
                case 3:
                    return STANDARD;
                case 4:
                    return HIGH;
                case 5:
                    return VERY_HIGH;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        LogManager.getLogger().error("Unable to parse " + text + " into an AtmosphericPressure. Returning STANDARD.");

        return STANDARD;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
