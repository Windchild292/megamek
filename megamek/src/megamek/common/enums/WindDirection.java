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
import megamek.client.generator.RandomGenderGenerator;

public enum WindDirection {

    /**
     * @param input the string to parse
     * @return the gender defined by the input, or a randomly generated string if the string isn't a
     * proper value
     */
    public static Gender parseFromString(String input) {
        try {
            return valueOf(input);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(input)) {
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

        MegaMek.getLogger().error("Failed to parse the gender value from input String " + input
                + ". Returning a newly generated gender.");
        return RandomGenderGenerator.generate();
    }
}
