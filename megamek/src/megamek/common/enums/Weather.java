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
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Weather {
    //region Enum Declarations
    CLEAR("Weather.CLEAR.text"),
    LIGHT_RAIN("Weather.LIGHT_RAIN.text"),
    MODERATE_RAIN("Weather.MODERATE_RAIN.text"),
    HEAVY_RAIN("Weather.HEAVY_RAIN.text"),
    GUSTING_RAIN("Weather.GUSTING_RAIN.text"),
    DOWNPOUR("Weather.DOWNPOUR.text"),
    LIGHT_SNOW("Weather.LIGHT_SNOW.text"),
    MODERATE_SNOW("Weather.MODERATE_SNOW.text"),
    SNOW_FLURRIES("Weather.SNOW_FLURRIES.text"),
    HEAVY_SNOW("Weather.HEAVY_SNOW.text"),
    SLEET("Weather.SLEET.text"),
    ICE_STORM("Weather.ICE_STORM.text"),
    LIGHT_HAIL("Weather.LIGHT_HAIL.text"),
    HEAVY_HAIL("Weather.HEAVY_HAIL.text"),
    LIGHTNING_STORM("Weather.LIGHTNING_STORM.text");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.enums", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Weather(String name) {
        this.name = resources.getString(name);
    }
    //endregion Constructors

    //region Boolean Comparisons
    public boolean isClear() {
        return this == CLEAR;
    }

    public boolean isLightRain() {
        return this == LIGHT_RAIN;
    }

    public boolean isModerateRain() {
        return this == MODERATE_RAIN;
    }

    public boolean isHeavyRain() {
        return this == HEAVY_RAIN;
    }

    public boolean isGustingRain() {
        return this == GUSTING_RAIN;
    }

    public boolean isDownpour() {
        return this == DOWNPOUR;
    }

    public boolean isLightSnow() {
        return this == LIGHT_SNOW;
    }

    public boolean isModerateSnow() {
        return this == MODERATE_SNOW;
    }

    public boolean isSnowFlurries() {
        return this == SNOW_FLURRIES;
    }

    public boolean isHeavySnow() {
        return this == HEAVY_SNOW;
    }

    public boolean isSleet() {
        return this == SLEET;
    }

    public boolean isIceStorm() {
        return this == ICE_STORM;
    }

    public boolean isLightHail() {
        return this == LIGHT_HAIL;
    }

    public boolean isHeavyHail() {
        return this == HEAVY_HAIL;
    }

    public boolean requiresLowTemperature() {
        return isLightSnow() || isModerateSnow() || isSnowFlurries() || isHeavySnow() || isSleet()
                || isIceStorm() || isLightHail() || isHeavyHail();
    }
    //endregion Boolean Comparisons


    /**
     * to-hit penalty for weather
     */
    public int getHitPenalty(Entity en) {
        switch (this) {
            case LIGHT_RAIN:
            case LIGHT_SNOW:
                return en.isConventionalInfantry() ? 1 : 0;
            case MODERATE_RAIN:
            case HEAVY_RAIN:
            case MODERATE_SNOW:
            case HEAVY_SNOW:
            case SLEET:
            case GUSTING_RAIN:
            case SNOW_FLURRIES:
                return 1;
            case DOWNPOUR:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * piloting penalty for weather
     */
    public int getPilotingPenalty() {
        switch (this) {
            case HEAVY_RAIN:
            case HEAVY_SNOW:
            case GUSTING_RAIN:
                return 1;
            case DOWNPOUR:
                return 2;
            default:
                return 0;
        }
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Weather, or CLEAR if there is an error in parsing
     */
    public static Weather parseFromString(String text) {
        try {
            valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return CLEAR;
                case 1:
                    return LIGHT_RAIN;
                case 2:
                    return MODERATE_RAIN;
                case 3:
                    return HEAVY_RAIN;
                case 4:
                    return GUSTING_RAIN;
                case 5:
                    return DOWNPOUR;
                case 6:
                    return LIGHT_SNOW;
                case 7:
                    return MODERATE_SNOW;
                case 8:
                    return SNOW_FLURRIES;
                case 9:
                    return HEAVY_SNOW;
                case 10:
                    return SLEET;
                case 12:
                    return ICE_STORM;
                case 13:
                    return LIGHT_HAIL;
                case 14:
                    return HEAVY_HAIL;
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Unable to parse " + text + " into a Weather. Returning CLEAR.");

        return CLEAR;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
