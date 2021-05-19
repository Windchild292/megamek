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
import megamek.common.Entity;
import megamek.common.EntityMovementMode;
import megamek.common.Mech;
import megamek.common.VTOL;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Wind {
    //region Enum Declarations
    CALM("Wind.CALM.text", "Wind.CALM.toolTipText"),
    LIGHT_GALE("Wind.LIGHT_GALE.text", "Wind.LIGHT_GALE.toolTipText"),
    MODERATE_GALE("Wind.MODERATE_GALE.text", "Wind.MODERATE_GALE.toolTipText"),
    STRONG_GALE("Wind.STRONG_GALE.text", "Wind.STRONG_GALE.toolTipText"),
    STORM("Wind.STORM.text", "Wind.STORM.toolTipText"),
    TORNADO_F13("Wind.TORNADO_F13.text", "Wind.TORNADO_F13.toolTipText"),
    TORNADO_F4("Wind.TORNADO_F4.text", "Wind.TORNADO_F4.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Wind(final String name, final String toolTipText) {
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
    public boolean isCalm() {
        return this == CALM;
    }

    public boolean isLightGale() {
        return this == LIGHT_GALE;
    }

    public boolean isModerateGale() {
        return this == MODERATE_GALE;
    }

    public boolean isStrongGale() {
        return this == STRONG_GALE;
    }

    public boolean isStorm() {
        return this == STORM;
    }

    public boolean isTornadoF13() {
        return this == TORNADO_F13;
    }

    public boolean isTornadoF4() {
        return this == TORNADO_F4;
    }

    public boolean isModerateGaleOrStronger() {
        return isModerateGale() || isStrongGaleOrStronger();
    }

    public boolean isStrongGaleOrStronger() {
        return isStrongGale() || isStormOrStronger();
    }

    public boolean isStormOrStronger() {
        return isStorm() || isTornado();
    }

    public boolean isTornado() {
        return isTornadoF13() || isTornadoF4();
    }
    //endregion Boolean Comparisons

    /**
     * piloting penalty for wind
     */
    public int getPilotingPenalty(final Entity entity) {
        switch (this) {
            case MODERATE_GALE:
                if ((entity instanceof VTOL) || (entity.getMovementMode() == EntityMovementMode.WIGE)) {
                    return 1;
                }
                break;
            case STRONG_GALE:
                if ((entity instanceof VTOL) || (entity.getMovementMode() == EntityMovementMode.WIGE)
                        || (entity.getMovementMode() == EntityMovementMode.HOVER)) {
                    return 2;
                } else if ((entity instanceof Mech) || (entity.isAirborne())) {
                    return 1;
                }
                break;
            case STORM:
                if ((entity instanceof VTOL) || (entity instanceof Mech)
                        || (entity.getMovementMode() == EntityMovementMode.WIGE)
                        || (entity.getMovementMode() == EntityMovementMode.HOVER)) {
                    return 3;
                } else if (entity.isAirborne()) {
                    return 2;
                }
                break;
            case TORNADO_F13:
                return 3;
            case TORNADO_F4:
                return 5;
            default:
                break;
        }

        return 0;
    }

    /**
     * @return the ignition modifier from the current weather
     */
    public int getIgnitionModifier() {
        switch (this) {
            case LIGHT_GALE:
            case MODERATE_GALE:
                return 2;
            case STRONG_GALE:
            case STORM:
                return 4;
            default:
                return 0;
        }
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Wind, or CALM if there is an error in parsing
     */
    public static Wind parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return CALM;
                case 1:
                    return LIGHT_GALE;
                case 2:
                    return MODERATE_GALE;
                case 3:
                    return STRONG_GALE;
                case 4:
                    return STORM;
                case 5:
                    return TORNADO_F13;
                case 6:
                    return TORNADO_F4;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a Wind. Returning CALM.");

        return CALM;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
