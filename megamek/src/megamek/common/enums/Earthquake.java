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

import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Earthquake {
    //region Enum Declarations
    NONE("Earthquake.NONE.text", "Earthquake.NONE.toolTipText"),
    TAINTED_CAUSTIC("Earthquake.TAINTED_CAUSTIC.text", "Earthquake.TAINTED_CAUSTIC.toolTipText"),
    TAINTED_RADIOLOGICAL("Earthquake.TAINTED_RADIOLOGICAL.text", "Earthquake.TAINTED_RADIOLOGICAL.toolTipText"),
    TAINTED_POISONOUS("Earthquake.TAINTED_POISONOUS.text", "Earthquake.TAINTED_POISONOUS.toolTipText"),
    TAINTED_FLAMMABLE("Earthquake.TAINTED_FLAMMABLE.text", "Earthquake.TAINTED_FLAMMABLE.toolTipText"),
    TOXIC_CAUSTIC("Earthquake.TOXIC_CAUSTIC.text", "Earthquake.TOXIC_CAUSTIC.toolTipText"),
    TOXIC_RADIOLOGICAL("Earthquake.TOXIC_RADIOLOGICAL.text", "Earthquake.TOXIC_RADIOLOGICAL.toolTipText"),
    TOXIC_POISONOUS("Earthquake.TOXIC_POISONOUS.text", "Earthquake.TOXIC_POISONOUS.toolTipText"),
    TOXIC_FLAMMABLE("Earthquake.TOXIC_FLAMMABLE.text", "Earthquake.TOXIC_FLAMMABLE.toolTipText"),
    BREATHABLE("Earthquake.BREATHABLE.text", "Earthquake.BREATHABLE.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Earthquake(final String name, final String toolTipText) {
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

    @Override
    public String toString() {
        return name;
    }
}
