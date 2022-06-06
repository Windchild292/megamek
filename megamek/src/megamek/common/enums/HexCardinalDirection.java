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
import megamek.common.Compute;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

/**
 * This enum contains the six cardinal directions when working with hexes, plus a randomization value.
 */
public enum HexCardinalDirection {
    //region Enum Declarations
    NORTH("HexCardinalDirection.NORTH.text", "HexCardinalDirection.NORTH.toolTipText", "HexCardinalDirection.NORTH.abbreviation"),
    NORTHEAST("HexCardinalDirection.NORTHEAST.text", "HexCardinalDirection.NORTHEAST.toolTipText", "HexCardinalDirection.NORTHEAST.abbreviation"),
    SOUTHEAST("HexCardinalDirection.SOUTHEAST.text", "HexCardinalDirection.SOUTHEAST.toolTipText", "HexCardinalDirection.SOUTHEAST.abbreviation"),
    SOUTH("HexCardinalDirection.SOUTH.text", "HexCardinalDirection.SOUTH.toolTipText", "HexCardinalDirection.SOUTH.abbreviation"),
    SOUTHWEST("HexCardinalDirection.SOUTHWEST.text", "HexCardinalDirection.SOUTHWEST.toolTipText", "HexCardinalDirection.SOUTHWEST.abbreviation"),
    NORTHWEST("HexCardinalDirection.NORTHWEST.text", "HexCardinalDirection.NORTHWEST.toolTipText", "HexCardinalDirection.NORTHWEST.abbreviation"),
    RANDOMIZE("HexCardinalDirection.RANDOMIZE.text", "HexCardinalDirection.RANDOMIZE.toolTipText", "HexCardinalDirection.RANDOMIZE.abbreviation");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    private final String abbreviation;
    //endregion Variable Declarations

    //region Constructors
    HexCardinalDirection(final String name, final String toolTipText, final String abbreviation) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
                PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
        this.abbreviation = resources.getString(abbreviation);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    //endregion Getters

    //region Boolean Comparisons
    public boolean isNorth() {
        return this == NORTH;
    }

    public boolean isNortheast() {
        return this == NORTHEAST;
    }

    public boolean isSoutheast() {
        return this == SOUTHEAST;
    }

    public boolean isSouth() {
        return this == SOUTH;
    }

    public boolean isSouthwest() {
        return this == SOUTHWEST;
    }

    public boolean isNorthwest() {
        return this == NORTHWEST;
    }

    public boolean isRandomize() {
        return this == RANDOMIZE;
    }
    //endregion Boolean Comparisons

    /**
     * @return an unbiased random direction, which will not be randomize
     */
    public static HexCardinalDirection getRandomDirection() {
        final HexCardinalDirection[] CardinalDirections = values();
        return CardinalDirections[Compute.randomInt(CardinalDirections.length - 1)];
    }

    /**
     * @param rotations positive to rotate clockwise, negative to rotate counterclockwise
     * @return the rotated direction
     */
    public HexCardinalDirection rotate(final int rotations) {
        final HexCardinalDirection[] CardinalDirections = values();
        return CardinalDirections[(ordinal() + rotations) % (CardinalDirections.length - 1)];
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the HexCardinalDirection, or RANDOMIZE if there is an error in parsing
     */
    public static HexCardinalDirection parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return NORTH;
                case 1:
                    return NORTHEAST;
                case 2:
                    return SOUTHEAST;
                case 3:
                    return SOUTH;
                case 4:
                    return SOUTHWEST;
                case 5:
                    return NORTHWEST;
                case 6:
                    return RANDOMIZE;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        try {
            switch (text) {
                case "N":
                    return NORTH;
                case "NE":
                    return NORTHEAST;
                case "SE":
                    return SOUTHEAST;
                case "S":
                    return SOUTH;
                case "SW":
                    return SOUTHWEST;
                case "NW":
                    return NORTHWEST;
                case "R":
                    return RANDOMIZE;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        LogManager.getLogger().error("Unable to parse " + text + " into a HexCardinalDirection. Returning RANDOMIZE.");

        return RANDOMIZE;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
