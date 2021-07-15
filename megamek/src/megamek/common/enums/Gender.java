/*
 * Copyright (C) 2020 - The MegaMek Team. All Rights Reserved
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
import megamek.client.generator.RandomGenderGenerator;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author's Note: This is for Biological Gender (strictly speaking, the term is Sex) only,
 * with the two OTHER-? flags being implemented here for MekHQ usage.
 */
public enum Gender {
    //region Enum Declarations
    MALE("Gender.MALE.text", "Gender.MALE.toolTipText", false),
    FEMALE("Gender.FEMALE.text", "Gender.FEMALE.toolTipText", false),
    OTHER_MALE("Gender.MALE.text", "Gender.MALE.toolTipText", true),
    OTHER_FEMALE("Gender.FEMALE.text", "Gender.FEMALE.toolTipText", true),
    RANDOMIZE("Gender.RANDOMIZE.text", "Gender.RANDOMIZE.toolTipText", true);
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    private final boolean internal;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
            PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    Gender(final String name, final String toolTipText, final boolean internal) {
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
        this.internal = internal;
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }

    /**
     * @return true if the enum value is only for internal use
     */
    public boolean isInternal() {
        return internal;
    }
    //endregion Getters

    //region Boolean Comparisons
    /**
     * @return true is the person's biological gender is male, otherwise false
     */
    public boolean isMale() {
        return (this == MALE) || (this == OTHER_MALE);
    }

    /**
     * @return true is the person's biological gender is female, otherwise false
     */
    public boolean isFemale() {
        return (this == FEMALE) || (this == OTHER_FEMALE);
    }
    //endregion Boolean Comparisons

    /**
     * @return a list of all external-facing gender options
     */
    public static List<Gender> getExternalOptions() {
        return Stream.of(values()).filter(gender -> !gender.isInternal()).collect(Collectors.toList());
    }

    /**
     * @return the external form of the internal gender
     */
    public Gender getExternalVariant() {
        return (this == OTHER_MALE) ? MALE : FEMALE;
    }

    /**
     * @return the internal form of the external gender
     */
    public Gender getInternalVariant() {
        return (this == MALE) ? OTHER_MALE : OTHER_FEMALE;
    }

    //region File I/O
    /**
     * @param text the string to parse
     * @return the gender defined by the text, or a randomly generated string if the string isn't a
     * proper value
     */
    public static Gender parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return MALE;
                case 1:
                    return FEMALE;
                case -1:
                default:
                    return RandomGenderGenerator.generate();
            }
        } catch (Exception ignored) {

        }

        MegaMek.getLogger().error("Failed to parse the gender value from text String " + text
                        + ". Returning a newly generated gender.");
        return RandomGenderGenerator.generate();
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
