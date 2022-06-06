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

import megamek.common.Compute;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

public enum Light {
    //region Enum Declarations
    SOLAR_FLARE("Light.SOLAR_FLARE.text", "Light.SOLAR_FLARE.toolTipText"),
    GLARE("Light.GLARE.text", "Light.GLARE.toolTipText"),
    DAWN("Light.DAWN.text", "Light.DAWN.toolTipText"),
    DAY("Light.DAY.text", "Light.DAY.toolTipText"),
    DUSK("Light.DUSK.text", "Light.DUSK.toolTipText"),
    FULL_MOON("Light.FULL_MOON.text", "Light.FULL_MOON.toolTipText"),
    MOONLESS_NIGHT("Light.MOONLESS_NIGHT.text", "Light.MOONLESS_NIGHT.toolTipText"),
    PITCH_BLACK("Light.PITCH_BLACK.text", "Light.PITCH_BLACK.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    Light(final String name, final String toolTipText) {
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
    public boolean isSolarFlare() {
        return this == SOLAR_FLARE;
    }

    public boolean isGlare() {
        return this == GLARE;
    }

    public boolean isDawn() {
        return this == DAWN;
    }

    public boolean isDay() {
        return this == DAY;
    }

    public boolean isDusk() {
        return this == DUSK;
    }

    public boolean isFullMoon() {
        return this == FULL_MOON;
    }

    public boolean isMoonlessNight() {
        return this == MOONLESS_NIGHT;
    }

    public boolean isPitchBlack() {
        return this == PITCH_BLACK;
    }

    public boolean isDawnOrDusk() {
        return isDawn() || isDusk();
    }

    public boolean isDawnDayOrDusk() {
        return isDay() || isDawn() || isDusk();
    }

    public boolean isGlareOrFullMoonNight() {
        return isGlare() || isFullMoon();
    }

    public boolean isSolarFlareOrMoonlessNight() {
        return isSolarFlare() || isMoonlessNight();
    }

    public boolean isNight() {
        return isFullMoon() || isMoonlessNight() || isPitchBlack();
    }
    //endregion Boolean Comparisons

    /**
     * @return the to-hit penalty caused by the current light levels
     */
    public int getHitPenalty(final boolean weapon) {
        switch (this) {
            case DUSK:
                return 1;
            case FULL_MOON:
                return 2;
            case MOONLESS_NIGHT:
                return weapon ? 3 : 1;
            case PITCH_BLACK:
                return weapon ? 4 : 2;
            default:
                return 0;
        }
    }

    /**
     * @return the heat bonus to hit for being overheated in darkness
     */
    public int getHeatBonus(final int heat) {
        final double divisor;
        switch (this) {
            case DUSK:
                divisor = 25.0;
                break;
            case FULL_MOON:
                divisor = 20.0;
                break;
            case MOONLESS_NIGHT:
                divisor = 15.0;
                break;
            case PITCH_BLACK:
                divisor = 10.0;
                break;
            default:
                return 0;
        }
        return (-1 * (int) Math.floor(heat / divisor));
    }

    /**
     * @return the piloting penalty for running/flanking/etc caused by the current light levels
     */
    public int getPilotingPenalty() {
        switch (this) {
            case MOONLESS_NIGHT:
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
    public static Light parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return DAY;
                case 1:
                    return (Compute.randomInt(2) == 1) ? DUSK : DAWN;
                case 2:
                    return FULL_MOON;
                case 3:
                    return MOONLESS_NIGHT;
                case 4:
                    return PITCH_BLACK;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        LogManager.getLogger().error("Unable to parse " + text + " into a Light. Returning DAY.");

        return DAY;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
