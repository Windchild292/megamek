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
    UNASSIGNED("TeamNumber.UNASSIGNED.text"),
    NONE("TeamNumber.NONE.text"),
    ONE("TeamNumber.ONE.text"),
    TWO("TeamNumber.TWO.text"),
    THREE("TeamNumber.THREE.text"),
    FOUR("TeamNumber.FOUR.text"),
    FIVE("TeamNumber.FIVE.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    //endregion Variable Declarations

    //region Constructors
    TeamNumber(final String name) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
                PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparison Methods
    public boolean isUnassigned() {
        return this == UNASSIGNED;
    }

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
