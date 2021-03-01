/*
 * Copyright (C) 2021 - The MegaMek Team. All Rights Reserved
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
package megamek.common.crew.enums;

public enum EdgeStyle {
    //region Enum Declarations
    DISABLED("EdgeStyle.DISABLED.text", "EdgeStyle.DISABLED.toolTipText"),
    A_TIME_OF_WAR("EdgeStyle.A_TIME_OF_WAR.text", "EdgeStyle.A_TIME_OF_WAR.toolTipText"),
    DESTINY("EdgeStyle.DESTINY.text", "EdgeStyle.DESTINY.toolTipText");//"MechWarrior: Destiny", "This is MechWarrior: Destiny-style edge rules. One or more of the dice may be rerolled when edge triggers. The system will automatically chose the optimal strategy for the player, .");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    EdgeStyle(final String name, final String toolTipText) {
        this.name = name;
        this.toolTipText = toolTipText;
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isEnabled() {
        return this != DISABLED;
    }

    public boolean isAToW() {
        return this == A_TIME_OF_WAR;
    }

    public boolean isDestiny() {
        return this == DESTINY;
    }
    //endregion Boolean Comparison Methods

    @Override
    public String toString() {
        return name;
    }
}
