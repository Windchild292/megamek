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
package megamek.common.enums;

public enum ReportType {
    //region Enum Declarations
    /**
     * Report Type: visible to all players.
     */
    PUBLIC,
    /**
     * Visible to all players, but all data marked for obscuration remains hidden.
     */
    OBSCURED,
    /**
     * Report is only visible to those players who can see the subject
     */
    HIDDEN,
    /**
     * Reports for testing only
     */
    TESTING,
    /**
     * Messages which should be sent only to the player indicated by "player"
     */
    PLAYER;
    //endregion Enum Declarations

    //region Boolean Comparison Methods
    public boolean isPublic() {
        return this == PUBLIC;
    }

    public boolean isObscured() {
        return this == OBSCURED;
    }

    public boolean isHidden() {
        return this == HIDDEN;
    }

    public boolean isTesting() {
        return this == TESTING;
    }

    public boolean isPlayer() {
        return this == PLAYER;
    }
    //endregion Boolean Comparison Methods
}