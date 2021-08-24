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

public enum BasementType {
    UNKNOWN("BasementType.UNKNOWN.text", "BasementType.UNKNOWN.toolTipText", 0),
    NONE("BasementType.NONE.text", "BasementType.NONE.toolTipText", 0),
    ONE_DEEP_FEET("BasementType.ONE_DEEP_FEET.text", "BasementType.ONE_DEEP_FEET.toolTipText", 1),
    ONE_DEEP_NORMAL("BasementType.ONE_DEEP_NORMAL.text", "BasementType.ONE_DEEP_NORMAL.toolTipText", 1),
    ONE_DEEP_NORMAL_INFANTRY_ONLY("BasementType.ONE_DEEP_NORMAL_INFANTRY_ONLY.text", "BasementType.ONE_DEEP_NORMAL_INFANTRY_ONLY.toolTipText", 1),
    ONE_DEEP_HEAD("BasementType.ONE_DEEP_HEAD.text", "BasementType.ONE_DEEP_HEAD.toolTipText", 1),
    TWO_DEEP_FEET("BasementType.TWO_DEEP_FEET.text", "BasementType.TWO_DEEP_FEET.toolTipText", 2),
    TWO_DEEP_HEAD("BasementType.TWO_DEEP_HEAD.text", "BasementType.TWO_DEEP_HEAD.toolTipText", 2);
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    private final int depth;
    //endregion Variable Declarations

    //region Constructors
    BasementType(final String name, final String toolTipText, final int depth) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
        this.depth = depth;
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }

    public int getDepth() {
        return depth;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isUnknown() {
        return this == UNKNOWN;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isOneDeepFeet() {
        return this == ONE_DEEP_FEET;
    }

    public boolean isOneDeepNormal() {
        return this == ONE_DEEP_NORMAL;
    }

    public boolean isOneDeepNormalInfantryOnly() {
        return this == ONE_DEEP_NORMAL_INFANTRY_ONLY;
    }

    public boolean isTwoDeepFeet() {
        return this == TWO_DEEP_FEET;
    }

    public boolean isTwoDeepHead() {
        return this == TWO_DEEP_HEAD;
    }
    //endregion Boolean Comparison Methods

    //region File I/O
    public static BasementType parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        if ("ONE_DEEP_NORMALINFONLY".equals(text)) {
            return ONE_DEEP_NORMAL_INFANTRY_ONLY;
        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a BasementType. Returning UNKNOWN.");

        return UNKNOWN;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
