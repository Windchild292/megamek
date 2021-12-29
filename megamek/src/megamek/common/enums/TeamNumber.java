/*
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
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

import java.util.ResourceBundle;

public enum TeamNumber {
    //region Enum Declarations
    NONE("TeamNumber.NONE.text", "TeamNumber.NONE.toolTipText"),
    ONE("TeamNumber.ONE.text", "TeamNumber.ONE.toolTipText"),
    TWO("TeamNumber.TWO.text", "TeamNumber.TWO.toolTipText"),
    THREE("TeamNumber.THREE.text", "TeamNumber.THREE.toolTipText"),
    FOUR("TeamNumber.FOUR.text", "TeamNumber.FOUR.toolTipText"),
    FIVE("TeamNumber.FIVE.text", "TeamNumber.FIVE.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    TeamNumber(final String name, final String toolTipText) {
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

    //region Boolean Comparison Methods
    public boolean isNone() {
        return this == NONE;
    }

    public boolean isOne() {
        return this == ONE;
    }

    public boolean isTwo() {
        return this == TWO;
    }

    public boolean isThree() {
        return this == THREE;
    }

    public boolean isFour() {
        return this == FOUR;
    }

    public boolean isFive() {
        return this == FIVE;
    }
    //endregion Boolean Comparison Methods

    @Override
    public String toString() {
        return name;
    }
}
