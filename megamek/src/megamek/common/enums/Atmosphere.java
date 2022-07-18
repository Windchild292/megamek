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

import megamek.MegaMek;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

/**
 * The different atmosphere types as per Tactical Operations: Advanced Rules. We keep Radiological
 * and Poisonous as separate options for MekHQ planetary usage.
 */
public enum Atmosphere {
    //region Enum Declarations
    NONE("Atmosphere.NONE.text", "Atmosphere.NONE.toolTipText"),
    TAINTED_CAUSTIC("Atmosphere.TAINTED_CAUSTIC.text", "Atmosphere.TAINTED_CAUSTIC.toolTipText"),
    TAINTED_RADIOLOGICAL("Atmosphere.TAINTED_RADIOLOGICAL.text", "Atmosphere.TAINTED_RADIOLOGICAL.toolTipText"),
    TAINTED_POISONOUS("Atmosphere.TAINTED_POISONOUS.text", "Atmosphere.TAINTED_POISONOUS.toolTipText"),
    TAINTED_FLAMMABLE("Atmosphere.TAINTED_FLAMMABLE.text", "Atmosphere.TAINTED_FLAMMABLE.toolTipText"),
    TOXIC_CAUSTIC("Atmosphere.TOXIC_CAUSTIC.text", "Atmosphere.TOXIC_CAUSTIC.toolTipText"),
    TOXIC_RADIOLOGICAL("Atmosphere.TOXIC_RADIOLOGICAL.text", "Atmosphere.TOXIC_RADIOLOGICAL.toolTipText"),
    TOXIC_POISONOUS("Atmosphere.TOXIC_POISONOUS.text", "Atmosphere.TOXIC_POISONOUS.toolTipText"),
    TOXIC_FLAMMABLE("Atmosphere.TOXIC_FLAMMABLE.text", "Atmosphere.TOXIC_FLAMMABLE.toolTipText"),
    BREATHABLE("Atmosphere.BREATHABLE.text", "Atmosphere.BREATHABLE.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    Atmosphere(final String name, final String toolTipText) {
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

    public boolean isTaintedCaustic() {
        return this == TAINTED_CAUSTIC;
    }

    public boolean isTaintedRadiological() {
        return this == TAINTED_RADIOLOGICAL;
    }

    public boolean isTaintedPoisonous() {
        return this == TAINTED_POISONOUS;
    }

    public boolean isTaintedFlammable() {
        return this == TAINTED_FLAMMABLE;
    }

    public boolean isToxicCaustic() {
        return this == TOXIC_CAUSTIC;
    }

    public boolean isToxicRadiological() {
        return this == TOXIC_RADIOLOGICAL;
    }

    public boolean isToxicPoisonous() {
        return this == TOXIC_POISONOUS;
    }

    public boolean isToxicFlammable() {
        return this == TOXIC_FLAMMABLE;
    }

    public boolean isBreathable() {
        return this == BREATHABLE;
    }
    //endregion Boolean Comparisons

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Atmosphere, or BREATHABLE if there is an error in parsing
     */
    public static Atmosphere parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (text) {
                case "None":
                    return NONE;
                case "TAINTEDPOISON":
                case "Tainted (Poisonous)":
                    return TAINTED_POISONOUS;
                case "TAINTEDCAUSTIC":
                case "Tainted (Caustic)":
                    return TAINTED_CAUSTIC;
                case "TAINTEDFLAME":
                case "Tainted (Flammable)":
                    return TAINTED_FLAMMABLE;
                case "TOXICPOISON":
                case "Toxic (Poisonous)":
                    return TOXIC_POISONOUS;
                case "TOXICCAUSTIC":
                case "Toxic (Caustic)":
                    return TOXIC_CAUSTIC;
                case "TOXICFLAME":
                case "Toxic (Flammable)":
                    return TOXIC_FLAMMABLE;
                case "BREATHABLE":
                case "Breathable":
                    return BREATHABLE;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        LogManager.getLogger().error("Unable to parse " + text + " into an Atmosphere. Returning BREATHABLE.");

        return BREATHABLE;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
