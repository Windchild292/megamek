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
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Weather {
    //region Enum Declarations
    CLEAR("Weather.CLEAR.text", "Weather.CLEAR.toolTipText"),
    LIGHT_RAIN("Weather.LIGHT_RAIN.text", "Weather.LIGHT_RAIN.toolTipText"),
    MODERATE_RAIN("Weather.MODERATE_RAIN.text", "Weather.MODERATE_RAIN.toolTipText"),
    HEAVY_RAIN("Weather.HEAVY_RAIN.text", "Weather.HEAVY_RAIN.toolTipText"),
    GUSTING_RAIN("Weather.GUSTING_RAIN.text", "Weather.GUSTING_RAIN.toolTipText"),
    DOWNPOUR("Weather.DOWNPOUR.text", "Weather.DOWNPOUR.toolTipText"),
    LIGHT_SNOW("Weather.LIGHT_SNOW.text", "Weather.LIGHT_SNOW.toolTipText"),
    MODERATE_SNOW("Weather.MODERATE_SNOW.text", "Weather.MODERATE_SNOW.toolTipText"),
    SNOW_FLURRIES("Weather.SNOW_FLURRIES.text", "Weather.SNOW_FLURRIES.toolTipText"),
    HEAVY_SNOW("Weather.HEAVY_SNOW.text", "Weather.HEAVY_SNOW.toolTipText"),
    SLEET("Weather.SLEET.text", "Weather.SLEET.toolTipText"),
    ICE_STORM("Weather.ICE_STORM.text", "Weather.ICE_STORM.toolTipText"),
    LIGHT_HAIL("Weather.LIGHT_HAIL.text", "Weather.LIGHT_HAIL.toolTipText"),
    HEAVY_HAIL("Weather.HEAVY_HAIL.text", "Weather.HEAVY_HAIL.toolTipText"),
    LIGHTNING_STORM("Weather.LIGHTNING_STORM.text", "Weather.LIGHTNING_STORM.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.enums",
            PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Weather(final String name, final String toolTipText) {
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

    public boolean isLightningStorm() {
        return this == LIGHTNING_STORM;
    }

    public boolean requiresLowTemperature() {
        return isLightSnow() || isModerateSnow() || isSnowFlurries() || isHeavySnow() || isSleet()
                || isIceStorm() || isLightHail() || isHeavyHail();
    }
    //endregion Boolean Comparisons

    /**
     * to-hit penalty for weather
     */
    public int getHitPenalty(final Entity entity) {
        switch (this) {
            case LIGHT_RAIN:
            case LIGHT_SNOW:
                return entity.isConventionalInfantry() ? 1 : 0;
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

    /**
     * @return the ignition modifier from the current weather
     */
    public int getIgnitionModifier() {
        switch (this) {
            case LIGHT_RAIN:
            case MODERATE_RAIN:
                return 1;
            case HEAVY_RAIN:
            case DOWNPOUR:
            case LIGHT_SNOW:
            case MODERATE_SNOW:
            case GUSTING_RAIN:
            case SNOW_FLURRIES:
                return 2;
            case HEAVY_SNOW:
            case LIGHT_HAIL:
            case HEAVY_HAIL:
                return 3;
            case ICE_STORM:
                return 4;
            default:
                return 0;
        }
    }

    /**
     * @return the modifier to extinguish a fire from the current weather
     */
    public int getExtinguishModifier() {
        switch (this) {
            case LIGHT_HAIL:
            case LIGHT_RAIN:
            case LIGHT_SNOW:
                return 1;
            case HEAVY_HAIL:
            case MODERATE_RAIN:
            case MODERATE_SNOW:
            case SNOW_FLURRIES:
                return 2;
            case HEAVY_RAIN:
            case GUSTING_RAIN:
            case HEAVY_SNOW:
                return 3;
            case DOWNPOUR:
                return 4;
            default:
                return -1;
        }
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the Weather, or CLEAR if there is an error in parsing
     */
    public static Weather parseFromString(final String text) {
        try {
            return valueOf(text);
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
                default:
                    break;
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
