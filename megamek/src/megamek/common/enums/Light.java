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
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Light {
    //region Enum Declarations
    DAY("Light.DAY.text"),
    DUSK("Light.DUSK.text"),
    FULL_MOON("Light.FULL_MOON.text"),
    MOONLESS("Light.MOONLESS.text"),
    PITCH_BLACK("Light.PITCH_BLACK.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Light(final String name) {
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparisons
    public boolean isDay() {
        return this == DAY;
    }

    public boolean isDusk() {
        return this == DUSK;
    }

    public boolean isFullMoon() {
        return this == FULL_MOON;
    }

    public boolean isMoonless() {
        return this == MOONLESS;
    }

    public boolean isPitchBlack() {
        return this == PITCH_BLACK;
    }

    public boolean isNight() {
        return isFullMoon() || isMoonless() || isPitchBlack();
    }
    //endregion Boolean Comparisons

    /**
     * to-hit penalty for light
     */
    public int getHitPenalty(boolean isWeapon) {
        switch (this) {
            case DUSK:
                return 1;
            case FULL_MOON:
                return 2;
            case MOONLESS:
                return isWeapon ? 3 : 1;
            case PITCH_BLACK:
                return isWeapon ? 4 : 2;
            default:
                return 0;
        }
    }

    /**
     * heat bonus to hit for being overheated in darkness
     */
    public int getHeatBonus(int heat) {
        double divisor;
        switch (this) {
            case DUSK:
                divisor = 25.0;
                break;
            case FULL_MOON:
                divisor = 20.0;
                break;
            case MOONLESS:
                divisor = 15.0;
                break;
            case PITCH_BLACK:
                divisor = 10.0;
                break;
            default:
                divisor = 10000.0;
                break;
        }
        return (-1 * (int) Math.floor(heat / divisor));
    }

    /**
     * piloting penalty for running/flanking/etc for light
     */
    public int getPilotingPenalty() {
        switch (this) {
            case MOONLESS:
                return 1;
            case PITCH_BLACK:
                return 2;
            default:
                return 0;
        }
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Light, or DAY if there is an error in parsing
     */
    public static Light parseFromString(String text) {
        try {
            valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 1:
                    return DUSK;
                case 2:
                    return FULL_MOON;
                case 3:
                    return MOONLESS;
                case 4:
                    return PITCH_BLACK;
                case 0:
                default:
                    return DAY;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a Light. Returning DAY.");

        return DAY;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
